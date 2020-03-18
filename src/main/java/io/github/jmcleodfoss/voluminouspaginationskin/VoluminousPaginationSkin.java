package io.github.jmcleodfoss.voluminouspaginationskin;

import com.sun.javafx.scene.control.skin.PaginationSkin;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Pagination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

/**
 *  A pagination skin with additional controls for use with paginations which
 *  must display a large number of pages. It adds the following controls to
 *  the navigation section:
 *  <ul>
 *  	<li>\\u23ee, Jump to first page</li>
 *  	<li>\\u23ea, Jump backward by 10%</li>
 *  	<li>\\u23a9, Jump forward by 10%</li>
 *  	<li>\\u23ed, Jump to last page</li>
 *  </ul>
 *
 *  This is for JavaFX 8. Later versions should extend
 *  javafx.scene.control.skin.PaginationSkin 
 *
 *  This was inspired by @see <a href="https://stackoverflow.com/questions/31540001">Stack Overflow Question 31540001</a>
 *  with code copied from @see <a href="github.com/openjdk/jfx/blob/master/modulres/javafx.controls/src/main/java/javafx/scene/control/skin/PaginationSkin.java">OpenJDK implementation of PaginationSkin</a>
 *
 *  CSS (including arrow shape definitions) are in @link{resources/css/VoluminousPaginationSkin.css}
 */
public class VoluminousPaginationSkin extends PaginationSkin
{
	/** The name of the properties file containing the accessible strings. */
	private final String RESOURCE_SOURCE = "io.github.jmcleodfoss.voluminouspaginationskin.accessibility";

	/** The relative distance by which to jump for jumpBackward and jumpForward */
	private static final double JUMP_FRACTION = 0.10;

	/** The container for all the navigation controls, constructed by the
	*   PaginationSkin constructor.
	*/
	private final HBox controlBox;

	/** The "next" arrow in the set of navigation controls. This is the last
	*   button created in this section by the parent class constructor, and
	*   is used to trigger addition of the new buttons when needed.
	*/
	private final Button nextArrowButton;

	/** The button to jump to the first page */
	private Button firstArrowButton;

	/** The button to jump backward by (Number of pages) * JUMP_FRACTION */
	private Button jumpBackwardButton;

	/** The button to jump forward by (Number of pages) * JUMP_FRACTION */
	private Button jumpForwardButton;

	/** The button to jump to the last page */
	private Button lastArrowButton;

	/** The resource bundle with the accessibility resources */
	private ResourceBundle accessibility;

	/** Create a skin for the given pagination.
	*	@param	pagination	The Paginiation object to skin
	*/
	public VoluminousPaginationSkin(final Pagination pagination)
	{
		super(pagination);

		if (accessibility == null)
			accessibility = ResourceBundle.getBundle(RESOURCE_SOURCE, Locale.getDefault(), this.getClass().getClassLoader());

		Node control = pagination.lookup(".control-box");
		assert control instanceof HBox;
		controlBox = (HBox)control;

		nextArrowButton = (Button)controlBox.getChildren().get(controlBox.getChildren().size()-1);
		double minButtonSize = nextArrowButton.getMinWidth();

		firstArrowButton = createNavigationButton("first-arrow", "first-arrow-button", "Accessibility.title.Pagination.FirstButton", minButtonSize);
		firstArrowButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent t)
			{
				getNode().requestFocus();
				pagination.setCurrentPageIndex(0);
			}
		});
		firstArrowButton.disableProperty().bind(pagination.currentPageIndexProperty().isEqualTo(0));

		jumpBackwardButton = createNavigationButton("jump-backward-arrow", "jump-backward-arrow-button", "Accessibility.title.Pagination.JumpForwardButton", minButtonSize);
		jumpBackwardButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent t)
			{
				int currentPage = pagination.getCurrentPageIndex();
				int target = Math.max(0, currentPage - jumpDistance());
				getNode().requestFocus();
				pagination.setCurrentPageIndex(target);
			}
		});
		jumpBackwardButton.disableProperty().bind(pagination.currentPageIndexProperty().lessThan(pagination.pageCountProperty().multiply(JUMP_FRACTION)));

		jumpForwardButton = createNavigationButton("jump-forward-arrow", "jump-forward-arrow-button", "Accessibility.title.Pagination.JumpBackwardButton", minButtonSize);
		jumpForwardButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent t)
			{
				int currentPage = pagination.getCurrentPageIndex();
				int maxPage = pagination.getPageCount();
				int target = Math.min(maxPage, currentPage + jumpDistance());
				getNode().requestFocus();
				pagination.setCurrentPageIndex(target);
			}
		});
		jumpForwardButton.disableProperty().bind(pagination.currentPageIndexProperty().greaterThan(pagination.pageCountProperty().multiply(1.0 - JUMP_FRACTION)));

		lastArrowButton = createNavigationButton("last-arrow", "last-arrow-button", "Accessibility.title.Pagination.LastButton", minButtonSize);
		lastArrowButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent t)
			{
				getNode().requestFocus();
				pagination.setCurrentPageIndex(pagination.getPageCount());
			}
		});
		lastArrowButton.disableProperty().bind(pagination.currentPageIndexProperty().isEqualTo(pagination.pageCountProperty().subtract(1)));

		controlBox.getChildren().addListener(new ListChangeListener<Node>(){
			@Override
			public void onChanged(ListChangeListener.Change c)
			{
				while (c.next()){
					if (	c.wasAdded()
					   && 	!c.wasRemoved()
					   &&	c.getAddedSize() == 1
					   &&   c.getAddedSubList().get(0) == nextArrowButton
					   &&  !controlBox.getChildren().contains(jumpBackwardButton)){
						updateNavigation();
					}
				}
			}
		});

		// Load the style sheet once the scene becomes available
		firstArrowButton.parentProperty().addListener(new ChangeListener<Parent>(){
			@Override
			public void changed(ObservableValue parent, Parent oldValue, Parent newValue)
			{
				if (firstArrowButton.getScene() != null)
					firstArrowButton.getScene().getStylesheets().add(getClass().getResource("/css/VoluminousPaginationSkin.css").toExternalForm());
			}
		});

		updateNavigation();
	}

	/** Create a navigation button.
 	*	@param	graphicCSSClass	The CSS class to use for the graphic
 	*				displayed in the button
 	*	@param	buttonCSSClass	The CSS class used for the button
 	*	@param	minButtonSize	The minimum button size used by all
 	*				buttons in the navigation container
 	*	@return	A button with the given styles associated with it.
 	*/
	private Button createNavigationButton(String graphicCSSClass, String buttonCSSClass, String accessibilityProperty, double minButtonSize)
	{
		StackPane pane = new StackPane();
		pane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		pane.getStyleClass().add(graphicCSSClass);

		Button button = new Button("");
		button.setMinSize(minButtonSize, minButtonSize);
		button.prefWidthProperty().bind(button.minWidthProperty());
		button.prefHeightProperty().bind(button.minWidthProperty());
		button.getStyleClass().add(buttonCSSClass);
		button.setFocusTraversable(false);
		button.setGraphic(pane);

		if (accessibility.keySet().contains(accessibilityProperty))
			button.setAccessibleText(accessibility.getString(accessibilityProperty));

		return button;
	}

	/** Calculate the distance to jump by based on the number of pages.
	*	@return	The distance to jump.
	*	@see JUMP_FRACTION
	*/
	private int jumpDistance()
	{
		Pagination pagination = getSkinnable();
		int maxPage = pagination.getPageCount();
		return (int)(maxPage * JUMP_FRACTION);
	}

	/** Add the new navigation buttons to the navigation container */
	private void updateNavigation()
	{
		controlBox.getChildren().add(0, jumpBackwardButton);
		controlBox.getChildren().add(0, firstArrowButton);
		controlBox.getChildren().add(jumpForwardButton);
		controlBox.getChildren().add(lastArrowButton);

		controlBox.applyCss();
	}
}
