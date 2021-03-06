package text;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ModifyColorMapDialog implements ActionListener, ChangeListener {
	protected static ModifyColorMapDialog self;

	protected JColorChooser chooser;

	protected Color original;
	protected int index;
	protected JComponent source;

	public static void showModifyColorMapDialog(JComponent source, int index) {
		if (self == null) {
			self = new ModifyColorMapDialog();
		}

		self.showDialogFor(source, index);
		TextSource.saveColorMap();
	}

	public ModifyColorMapDialog() {
		chooser = new JColorChooser();
		chooser.setPreviewPanel(null);
		chooser.getSelectionModel().addChangeListener(this);
	}

	public void showDialogFor(JComponent _source, int _index) {
		index = _index;
		original = TextSource.colorTable[index];
		source = _source;

		chooser.setColor(original);
		JColorChooser.createDialog(null, "Edit Color (" + index + ")", true, chooser, null, this).show();
	}

	@Override
	public void stateChanged(ChangeEvent ev) {
		Color newColor = ((ColorSelectionModel) ev.getSource()).getSelectedColor();
		TextSource.colorTable[index] = newColor;

		source.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		TextSource.colorTable[index] = original;

		source.repaint();
	}
}
