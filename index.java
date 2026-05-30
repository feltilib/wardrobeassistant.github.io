// ==================== WardrobeItem.java ====================

public class WardrobeItem {
    private String name;
    private String type; // e.g., Top, Bottom, Hijab
    private String color;
    private String material;
    private String seasonality;
    private String hijabCompatibility;

    public WardrobeItem(String name, String type, String color, String material, String seasonality, String hijabCompatibility) {
        this.name = name;
        this.type = type;
        this.color = color;
        this.material = material;
        this.seasonality = seasonality;
        this.hijabCompatibility = hijabCompatibility;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public String getMaterial() {
        return material;
    }

    public String getSeasonality() {
        return seasonality;
    }

    public String getHijabCompatibility() {
        return hijabCompatibility;
    }

    @Override
    public String toString() {
        return name + " (" + type + ", " + color + ", " + material + ", " + seasonality + ", Hijab Compatible: " + hijabCompatibility + ")";
    }
}


// ==================== Outfit.java ====================

import java.time.LocalDate;

public class Outfit {
    private WardrobeItem top;
    private WardrobeItem bottom;
    private WardrobeItem hijab;
    private String occasion;
    private LocalDate dateCreated;

    public Outfit(WardrobeItem top, WardrobeItem bottom, WardrobeItem hijab, String occasion) {
        this.top = top;
        this.bottom = bottom;
        this.hijab = hijab;
        this.occasion = occasion;
        this.dateCreated = LocalDate.now();
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setTop(WardrobeItem top) {
        this.top = top;
    }

    public void setBottom(WardrobeItem bottom) {
        this.bottom = bottom;
    }

    public void setHijab(WardrobeItem hijab) {
        this.hijab = hijab;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    @Override
    public String toString() {
        return "Outfit: Top - " + (top != null ? top.getName() : "None") +
               ", Bottom - " + (bottom != null ? bottom.getName() : "None") +
               ", Hijab - " + (hijab != null ? hijab.getName() : "None") +
               ", Occasion: " + occasion;
    }
}


// ==================== WardrobeManager.java ====================

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WardrobeManager {
    private ArrayList<WardrobeItem> items = new ArrayList<>();
    private ArrayList<Outfit> outfits = new ArrayList<>();

    public WardrobeManager() {
        JFrame frame = new JFrame("Wardrobe Assistant");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 182, 193));
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        JButton addItemButton = createStyledButton("Add Item");
        JButton createOutfitButton = createStyledButton("Create Outfit");
        JButton viewCalendarButton = createStyledButton("View Outfit Calendar");
        JButton editOutfitButton = createStyledButton("Edit Outfit");

        panel.add(addItemButton);
        panel.add(createOutfitButton);
        panel.add(viewCalendarButton);
        panel.add(editOutfitButton);

        frame.add(panel);
        frame.setVisible(true);

        addItemButton.addActionListener(new AddItemListener());
        createOutfitButton.addActionListener(new CreateOutfitListener());
        viewCalendarButton.addActionListener(new ViewCalendarListener());
        editOutfitButton.addActionListener(new EditOutfitListener());
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.PINK, 2));
        return button;
    }

    private class AddItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = JOptionPane.showInputDialog("Enter item name:");
            if (name == null || name.isBlank()) return;

            String type = JOptionPane.showInputDialog("Enter item type (Top, Bottom, Hijab):");
            String color = JOptionPane.showInputDialog("Enter item color:");
            String material = JOptionPane.showInputDialog("Enter item material:");
            String seasonality = JOptionPane.showInputDialog("Enter seasonality (e.g., Summer, Winter):");
            String hijabCompatibility = JOptionPane.showInputDialog("Enter hijab compatibility:");

            WardrobeItem item = new WardrobeItem(name.trim(), type.trim(), color.trim(), material.trim(), seasonality.trim(), hijabCompatibility.trim());
            items.add(item);
            JOptionPane.showMessageDialog(null, "Item added successfully!");
        }
    }

    private class CreateOutfitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (items.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No items available. Add items first.");
                return;
            }

            WardrobeItem top = promptForItem("Top");
            if (top == null) return;

            WardrobeItem bottom = promptForItem("Bottom");
            if (bottom == null) return;

            WardrobeItem hijab = promptForItem("Hijab");
            if (hijab == null) return;

            String occasion = JOptionPane.showInputDialog("Enter occasion:");
            if (occasion == null || occasion.isBlank()) {
                JOptionPane.showMessageDialog(null, "Occasion cannot be empty.");
                return;
            }

            Outfit outfit = new Outfit(top, bottom, hijab, occasion.trim());
            outfits.add(outfit);
            JOptionPane.showMessageDialog(null, "Outfit created successfully!");
        }
    }

    private class ViewCalendarListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (outfits.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No outfits in the calendar.");
                return;
            }

            JTextArea calendarDisplay = new JTextArea(15, 50);
            calendarDisplay.setEditable(false);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy");

            for (Outfit outfit : outfits) {
                calendarDisplay.append(outfit.getDateCreated().format(formatter) + " - " + outfit + "\n");
            }

            JScrollPane scrollPane = new JScrollPane(calendarDisplay);
            JOptionPane.showMessageDialog(null, scrollPane, "Outfit Calendar", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class EditOutfitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (outfits.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No outfits available to edit. Create an outfit first.");
                return;
            }

            String dateStr = JOptionPane.showInputDialog("Enter the date of the outfit to edit (yyyy-mm-dd):");
            if (dateStr == null || dateStr.isBlank()) {
                JOptionPane.showMessageDialog(null, "Date cannot be empty.");
                return;
            }

            LocalDate date;
            try {
                date = LocalDate.parse(dateStr.trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid date format. Please use yyyy-mm-dd.");
                return;
            }

            Outfit outfit = findOutfitByDate(date);
            if (outfit == null) {
                JOptionPane.showMessageDialog(null, "No outfit found for the given date.");
                return;
            }

            JOptionPane.showMessageDialog(null, "Editing Outfit:\n" + outfit);

            WardrobeItem newTop = promptForItem("new Top (or leave blank to keep current)");
            if (newTop != null) {
                outfit.setTop(newTop);
                JOptionPane.showMessageDialog(null, "Top updated successfully!");
            }

            WardrobeItem newBottom = promptForItem("new Bottom (or leave blank to keep current)");
            if (newBottom != null) {
                outfit.setBottom(newBottom);
                JOptionPane.showMessageDialog(null, "Bottom updated successfully!");
            }

            WardrobeItem newHijab = promptForItem("new Hijab (or leave blank to keep current)");
            if (newHijab != null) {
                outfit.setHijab(newHijab);
                JOptionPane.showMessageDialog(null, "Hijab updated successfully!");
            }

            String newOccasion = JOptionPane.showInputDialog("Enter new occasion (or leave blank to keep current):");
            if (newOccasion != null && !newOccasion.isBlank()) {
                outfit.setOccasion(newOccasion.trim());
                JOptionPane.showMessageDialog(null, "Occasion updated successfully!");
            }

            JOptionPane.showMessageDialog(null, "Outfit updated:\n" + outfit);
        }
    }

    private WardrobeItem promptForItem(String type) {
        String name = JOptionPane.showInputDialog("Enter " + type + " name:");
        for (WardrobeItem item : items) {
            if (item.getName().equalsIgnoreCase(name.trim())) {
                return item;
            }
        }
        JOptionPane.showMessageDialog(null, type + " not found. Please add it first.");
        return null;
    }

    private Outfit findOutfitByDate(LocalDate date) {
        for (Outfit outfit : outfits) {
            if (outfit.getDateCreated().equals(date)) {
                return outfit;
            }
        }
        return null;
    }
}


// ==================== Main.java ====================

public class Main {
    public static void main(String[] args) {
        new WardrobeManager(); // Launch the wardrobe manager GUI
    }
}
