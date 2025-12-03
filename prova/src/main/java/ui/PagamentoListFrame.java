package ui.pagamento;

import dao.PessoaPagamentoDAO;
import domain.PessoaPagamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PagamentoListFrame extends JFrame {

    private final PessoaPagamentoDAO dao = new PessoaPagamentoDAO();

    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID","Nome","CPF","Modo","Valor","Mensagem","Data/Hora"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };

    private final JTable table = new JTable(model);
    private final JButton bAtualizar = new JButton("Atualizar");

    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public PagamentoListFrame() {
        super("Pagamentos");
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(bAtualizar);
        add(south, BorderLayout.SOUTH);

        bAtualizar.addActionListener(e -> carregar());

        setSize(900, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        carregar();
    }

    private void carregar() {
        model.setRowCount(0);
        List<PessoaPagamento> lista = dao.listar();
        for (PessoaPagamento p : lista) {
            String dataHora = p.getDataHora() != null ? p.getDataHora().format(fmt) : "";
            model.addRow(new Object[]{
                    p.getCodigo(),
                    p.getNome(),
                    p.getCpf(),
                    p.getModo(),
                    p.getValor(),
                    p.getRetmsg(),
                    dataHora
            });
        }
    }
}
