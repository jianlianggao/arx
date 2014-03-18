package org.deidentifier.arx.gui.view.impl.wizard.importdata;

import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;


public class PreviewPage extends WizardPage {

    private ImportDataWizard wizardImport;

    private Table table;
    private TableViewer tableViewer;


    public PreviewPage(ImportDataWizard wizardImport)
    {

        super("WizardImportPreviewPage");

        this.wizardImport = wizardImport;

        setTitle("Preview");
        setDescription("Please check whether everything is right");

    }

    public void createControl(Composite parent)
    {

        Composite container = new Composite(parent, SWT.NULL);

        setControl(container);
        container.setLayout(new GridLayout(1, false));

        tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
        tableViewer.setContentProvider(new ArrayContentProvider());

        table = tableViewer.getTable();
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        table.setLinesVisible(true);
        table.setHeaderVisible(true);

        setPageComplete(false);

    }

    @Override
    public void setVisible(boolean visible)
    {

        super.setVisible(visible);

        if (visible) {

            table.setRedraw(false);

            while (table.getColumnCount() > 0) {

                table.getColumns()[0].dispose();

            }

            List<ImportDataColumn> columns = wizardImport.getData().getColumns();

            for (int i = 0; i < columns.size(); i++) {

                ImportDataColumn column = columns.get(i);

                if (column.isEnabled()) {

                    TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
                    tableViewerColumn.setLabelProvider(new PreviewColumnLabelProvider(i));

                    TableColumn tblclmnColumn = tableViewerColumn.getColumn();
                    tblclmnColumn.setToolTipText("Datatype: " + column.getDatatype());
                    tblclmnColumn.setWidth(100);
                    tblclmnColumn.setText(column.getName());

                }

            }

            tableViewer.setInput(wizardImport.getData().getPreviewData());

            table.layout();
            table.setRedraw(true);

            setPageComplete(true);

        } else {

            setPageComplete(false);

        }

    }

    private class PreviewColumnLabelProvider extends ColumnLabelProvider {

        private int column;

        public PreviewColumnLabelProvider(int column) {

            this.column = column;

        }

        @Override
        public String getText(Object element) {

            return ((String[]) element)[column];

        }

    }

}
