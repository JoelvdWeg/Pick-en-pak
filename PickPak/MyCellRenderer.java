package PickPak;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MyCellRenderer extends DefaultTableCellRenderer
{
    public Component getTableCellRendererComponent(JTable table, boolean huidigePick, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component c = super.getTableCellRendererComponent(table, huidigePick, isSelected, hasFocus, row, column);
        if (huidigePick)
        {
            c.setBackground(Color.YELLOW);
        }
        else
        {
            c.setBackground(Color.YELLOW);
        }
        return c;
    }
}