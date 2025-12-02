package ui;

import javax.swing.*;
import java.awt.*;

import ui.aluno.AlunoFormFrame;
import ui.aluno.AlunoListFrame;
import ui.PagamentoFrame;   // 👈 importa a tela de pagamento

public class MainFrame extends JFrame {

    public MainFrame() {
        super("Sistema");
        setJMenuBar(buildMenu());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

    private JMenuBar buildMenu() {
        JMenuBar mb = new JMenuBar();

        // MENU CADASTRAR (já existia)
        JMenu mCadastrar = new JMenu("Cadastrar");

        JMenuItem miAluno = new JMenuItem("Aluno");
        miAluno.addActionListener(e -> new AlunoFormFrame().setVisible(true));
        mCadastrar.add(miAluno);

        JMenuItem miListar = new JMenuItem("Listar Alunos");
        miListar.addActionListener(e -> new AlunoListFrame().setVisible(true));
        mCadastrar.add(miListar);

        mb.add(mCadastrar);

        // MENU PAGAMENTOS (novo)
        JMenu mPagamentos = new JMenu("Pagamentos");

        JMenuItem miNovoPagamento = new JMenuItem("Novo Pagamento");
        miNovoPagamento.addActionListener(e -> new PagamentoFrame().setVisible(true));
        mPagamentos.add(miNovoPagamento);

        mb.add(mPagamentos);

        return mb;
    }
}