package rero.dck.items;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import rero.config.*;
import rero.dck.*;

public class OptionInput extends SuperInput implements ItemListener
{
   protected JLabel    label;
   protected String    defaultVal;

   protected JComboBox select;

   public OptionInput(String var, String _defaultVal, String values[], String _label, char mnemonic, int rightGap)
   {
      label = new JLabel(_label);

      setLayout(new BorderLayout());
     
      select = new JComboBox(values);
      select.setEditable(true); 

      add(label,  BorderLayout.WEST);
      add(select, BorderLayout.CENTER);

      if (rightGap > 0)
      {
         JPanel temp = new JPanel();
         temp.setPreferredSize(new Dimension(rightGap, 0));

         add(temp, BorderLayout.EAST);
      }

      label.setDisplayedMnemonic(mnemonic);
      select.addItemListener(this);

      variable   = var;
 
      defaultVal = _defaultVal; 
   }

   public void setEnabled(boolean b)
   {
      Component[] blah = getComponents();
      for (int x = 0; x < blah.length; x++)
      {
         blah[x].setEnabled(b);
      }

      super.setEnabled(b);
   }

   public void save()
   {
      ClientState.getClientState().setString(getVariable(), select.getSelectedItem().toString());
   }

   public int getEstimatedWidth()
   {
      return (int)label.getPreferredSize().getWidth();
   }

   public void setAlignWidth(int width)
   {
      label.setPreferredSize(new Dimension(width, 0));
      revalidate();
   }

   public JComponent getComponent()
   {
      return this;
   }

   public void refresh()
   {
      select.setSelectedItem(ClientState.getClientState().getString(getVariable(), defaultVal));
   }

   public void itemStateChanged(ItemEvent ev)
   {
      notifyParent();
   }
}


