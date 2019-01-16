package controller;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.ButtonBase;
import javafx.scene.input.MouseEvent;

public class MouseEventHandler implements EventHandler<MouseEvent> {

	@Override
	public void handle(final MouseEvent ME) {

		Object obj = ME.getSource();

		// 모든 버튼을 포함하는 클래스
		ButtonBase button = (ButtonBase) obj;
		button.setCursor(Cursor.HAND);

	}
}