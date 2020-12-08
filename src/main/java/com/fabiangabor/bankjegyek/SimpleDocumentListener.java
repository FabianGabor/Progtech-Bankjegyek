package com.fabiangabor.bankjegyek;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Dokumentum eseményfigyelő (insert, remove, changed)
 */
public interface SimpleDocumentListener extends DocumentListener {
    /**
     * @param e Dokumentum változtatásának az eseménye
     */
    void update(DocumentEvent e);

    @Override
    default void insertUpdate(DocumentEvent e) {
        update(e);
    }
    @Override
    default void removeUpdate(DocumentEvent e) {
        update(e);
    }
    @Override
    default void changedUpdate(DocumentEvent e) {
        update(e);
    }
}
