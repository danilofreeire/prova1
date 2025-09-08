package ui.aluno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import dao.AlunoDAO;
import domain.Aluno;

public class AlunoListFrame extends JFrame {
    private final AlunoDAO dao = new AlunoDAO();

    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID","Nome","Matrícula","Email","Curso"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };

    private final JTable table = new JTable(model);
    private final JButton bAtualizar = new JButton("Atualizar");
    private final JButton bEditar = new JButton("Editar");
    private final JButton bExcluir = new JButton("Excluir");

    public AlunoListFrame() {
        super("Alunos");
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        south.add(bAtualizar); south.add(bEditar); south.add(bExcluir);
        add(south, BorderLayout.SOUTH);

        bAtualizar.addActionListener(e -> carregar());
        bExcluir.addActionListener(e -> excluir());
        bEditar.addActionListener(e -> editar());

        setSize(700, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        carregar();
    }

    private void carregar() {
        model.setRowCount(0);
        List<Aluno> alunos = dao.listar();
        for (Aluno a : alunos) {
            model.addRow(new Object[]{a.getId(), a.getNome(), a.getMatricula(), a.getEmail(), a.getCurso()});
        }
    }

    private void excluir() {
        int r = table.getSelectedRow(); if (r < 0) return;
        Long id = (Long) model.getValueAt(r, 0);
        dao.excluir(id);
        carregar();
    }

    private void editar() {
        int r = table.getSelectedRow(); if (r < 0) return;

        Long id = (Long) model.getValueAt(r, 0);
        JTextField fn = new JTextField((String) model.getValueAt(r, 1));
        JTextField fm = new JTextField((String) model.getValueAt(r, 2));
        JTextField fe = new JTextField((String) model.getValueAt(r, 3));
        JTextField fc = new JTextField((String) model.getValueAt(r, 4));

        JPanel form = new JPanel(new GridLayout(0, 2, 6, 6));
        form.add(new JLabel("Nome:"));      form.add(fn);
        form.add(new JLabel("Matrícula:")); form.add(fm);
        form.add(new JLabel("Email:"));     form.add(fe);
        form.add(new JLabel("Curso:"));     form.add(fc);

        int ok = JOptionPane.showConfirmDialog(this, form, "Editar", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            Aluno a = new Aluno();
            a.setId(id);
            a.setNome(fn.getText().trim());
            a.setMatricula(fm.getText().trim());
            a.setEmail(fe.getText().trim());
            a.setCurso(fc.getText().trim());
            dao.salvar(a);
            carregar();
        }
    }
}
