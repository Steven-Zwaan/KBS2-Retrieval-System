import Models.Product;
import Models.ProductList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class StockScreen extends JPanel implements ActionListener {

    JList<Product> voorraadList;
    ProductList productList;
    int index;
    public StockScreen() {
        this.setLayout(new BorderLayout());
        productList = new ProductList();
        productList.getProductsFromDatabase();


        voorraadList = new JList(productList.getProductList().toArray());
        JScrollPane scrollPaneStockScreen = new JScrollPane(voorraadList);

        scrollPaneStockScreen.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneStockScreen.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneStockScreen.getVerticalScrollBar().setUnitIncrement(16);

        JPanel selectedStockScreen = new JPanel(new FlowLayout(FlowLayout.LEFT));

        this.add(scrollPaneStockScreen, BorderLayout.CENTER);

        // Bottom bar (Selected bar)
        JButton buttonAanpassenStock = new JButton("Aanpassen");
        buttonAanpassenStock.setActionCommand("AanpassenStock");
        buttonAanpassenStock.addActionListener(this);

        JTextField zoekenStock = new JTextField(10);
        zoekenStock.setText("Zoeken...");
        zoekenStock.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (zoekenStock.getText().equals("Zoeken...")) {
                    zoekenStock.setText("");
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (zoekenStock.getText().isEmpty()) {
                    zoekenStock.setText("Zoeken...");
                }

            }
        });

        zoekenStock.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterStock(voorraadList, productList.getProductList());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterStock(voorraadList, productList.getProductList());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterStock(voorraadList, productList.getProductList());
            }

            public void filterStock(JList<Product> product, List<Product> productList) {
                ArrayList<Product> foundStocks = new ArrayList<>();
                try {
                    int orderID = Integer.parseInt(zoekenStock.getText());
                    for (Product foundStock : productList) {
                        if (String.valueOf(foundStock.getId()).contains(String.valueOf(orderID))) {
                            foundStocks.add(foundStock);
                        }
                    }
//					if (foundStocks.size() >0){
                    product.setListData(foundStocks.toArray(new Product[0]));
//
//					}
                } catch (NumberFormatException e) {
                    String orderNaam = zoekenStock.getText();
                    if (orderNaam.equals("Zoeken...")) {
                        foundStocks.addAll(productList);
                    } else {
                        for (Product foundStock : productList) {
                            if (String.valueOf(foundStock.getName()).contains(String.valueOf(orderNaam))) {
                                foundStocks.add(foundStock);
                            }
                        }
                        product.setListData(foundStocks.toArray(new Product[0]));

                    }

                }
                product.setListData(foundStocks.toArray(new Product[0]));
                if(foundStocks.size() == 0) {
                    zoekenStock.setBackground(Color.RED);
                } else {
                    zoekenStock.setBackground(Color.white);
                }
            }
        });



        JLabel selectedProductLabel = new JLabel(" ");

        voorraadList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(voorraadList.getSelectedIndex() != 0) {
                    try {
                        selectedProductLabel.setText(productList.getProductList().get(voorraadList.getSelectedIndex()).toString());
                    } catch (IndexOutOfBoundsException exception) {
                        selectedProductLabel.setText(String.valueOf(voorraadList.getModel().getElementAt(0)));
                    }
                } else {
                    selectedProductLabel.setText(String.valueOf(voorraadList.getModel().getElementAt(0)));
                }
                index = voorraadList.getSelectedIndex();
            }
        });



        selectedStockScreen.add(buttonAanpassenStock);
        selectedStockScreen.add(zoekenStock);
        selectedStockScreen.add(selectedProductLabel);

        this.add(selectedStockScreen, BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("AanpassenStock")) {
            StockScreenEditPopup popup = new StockScreenEditPopup(productList.getProductList().get(index), "Change stock of '" + productList.getProductList().get(index).getName() + "'", productList.getProductList().get(index).getStock(), this);
            productList.getProductList().get(index).setStockFromDatabase();
            this.voorraadList.revalidate();
        }
    }
}
