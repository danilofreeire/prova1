package ui.aluno;

import javax.swing.*;
import java.awt.*;
import dao.AlunoDAO;
import domain.Aluno;

public class AlunoFormFrame extends JFrame {
    private final JTextField tNome = new JTextField(20);
    private final JTextField tMatricula = new JTextField(15);
    private final JTextField tEmail = new JTextField(20);
    private final JTextField tCurso = new JTextField(20);
    private final JButton bSalvar = new JButton("Salvar");
    private final JButton bLimpar = new JButton("Limpar");
    private final AlunoDAO dao = new AlunoDAO();

    public AlunoFormFrame() {
        super("Cadastro de Aluno");
        setLayout(new GridLayout(0, 2, 6, 6));

        add(new JLabel("Nome:"));      add(tNome);
        add(new JLabel("Matrícula:")); add(tMatricula);
        add(new JLabel("Email:"));     add(tEmail);
        add(new JLabel("Curso:"));     add(tCurso);
        add(bSalvar);                  add(bLimpar);

        bSalvar.addActionListener(e -> {
            Aluno a = new Aluno();
            a.setNome(tNome.getText().trim());
            a.setMatricula(tMatricula.getText().trim());
            a.setEmail(tEmail.getText().trim());
            a.setCurso(tCurso.getText().trim());
            dao.salvar(a);
            tNome.setText(""); tMatricula.setText(""); tEmail.setText(""); tCurso.setText("");
            JOptionPane.showMessageDialog(this, "Salvo!");
        });

        bLimpar.addActionListener(e -> {
            tNome.setText(""); tMatricula.setText(""); tEmail.setText(""); tCurso.setText("");
        });

        setSize(420, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
