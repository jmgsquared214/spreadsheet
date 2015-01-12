package com.vaadin.addon.spreadsheet.action;

import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

import com.vaadin.addon.spreadsheet.Spreadsheet;
import com.vaadin.addon.spreadsheet.Spreadsheet.SelectionChangeEvent;

/**
 * Spreadsheet action for showing or hiding a cell comment.
 * 
 * @author Vaadin Ltd.
 * @since 1.0
 */
@SuppressWarnings("serial")
public class ShowHideCellCommentAction extends SpreadsheetAction {

    public ShowHideCellCommentAction() {
        super("");
    }

    @Override
    public boolean isApplicableForSelection(Spreadsheet spreadsheet,
            SelectionChangeEvent event) {
        if (!spreadsheet.getActiveSheet().getProtect()) {
            if (event.getCellRangeAddresses().length == 0
                    && event.getIndividualSelectedCells().length == 0) {
                CellReference cr = event.getSelectedCellReference();
                Comment cellComment = spreadsheet.getActiveSheet()
                        .getCellComment(cr.getRow(), cr.getCol());
                if (cellComment != null) {
                    if (cellComment.isVisible()) {
                        setCaption("Hide comment");
                    } else {
                        setCaption("Show comment");
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isApplicableForHeader(Spreadsheet spreadsheet,
            CellRangeAddress headerRange) {
        return false;
    }

    @Override
    public void executeActionOnSelection(Spreadsheet spreadsheet,
            SelectionChangeEvent event) {
        CellReference cr = event.getSelectedCellReference();
        Comment cellComment = spreadsheet.getActiveSheet().getCellComment(
                cr.getRow(), cr.getCol());
        cellComment.setVisible(!cellComment.isVisible());
        spreadsheet.updateMarkedCells();
    }

    @Override
    public void executeActionOnHeader(Spreadsheet spreadsheet,
            CellRangeAddress headerRange) {
        throw new UnsupportedOperationException(
                "Hide header action can't be executed against a selection.");
    }

}
