package com.simple.table.view;

import com.simple.table.model.Funcionario;
import com.simple.table.controller.FuncionarioController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JanelaPrincipal extends JFrame {

    private final JTable tabela;
    private final DefaultTableModel modeloTabela;
    private final List<Funcionario> funcionarios;
    private List<Funcionario> funcionariosOriginais;

    public JanelaPrincipal() {
        setTitle("Gerenciamento de Funcionários");
        setSize(1800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        funcionarios = new ArrayList<>();

        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Data Nascimento");
        modeloTabela.addColumn("Salário");
        modeloTabela.addColumn("Função");

        tabela = new JTable(modeloTabela);
        JScrollPane painelRolagem = new JScrollPane(tabela);
        add(painelRolagem, BorderLayout.CENTER);

        atualizarTabela();

        criarPainelBotoes();

    }

    private void carregarFuncionarios() {
        funcionarios.clear();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        funcionariosOriginais = new ArrayList<>();
        for (Funcionario f : funcionarios) {
            funcionariosOriginais.add(new Funcionario(f.getNome(), f.getDataNascimento(), f.getSalario(), f.getFuncao()));
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);

        for (Funcionario f : funcionarios) {
            modeloTabela.addRow(new Object[]{
                f.getNome(),
                f.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                "R$ " + new DecimalFormat("#,##0.00").format(f.getSalario()),
                f.getFuncao()
            });
        }
    }

    private void removerFuncionario(List<Funcionario> funcionarios, String nome) {
        FuncionarioController.removerFuncionario(funcionarios, nome);
        atualizarTabela();
    }

    private void aumentarSalario() {
        FuncionarioController.aumentarSalario(funcionarios);
        atualizarTabela();
    }

    private void ordenarFuncionarios() {
        FuncionarioController.ordenarFuncionarios(funcionarios);
        atualizarTabela();
    }

    private void exibirAniversariantes() {
        JOptionPane.showMessageDialog(this, FuncionarioController.exibirAniversariantes(funcionarios), "Aniversariantes", JOptionPane.INFORMATION_MESSAGE);
    }

    private void calcularTotalSalarios() {
        JOptionPane.showMessageDialog(this, FuncionarioController.calcularTotalSalarios(funcionarios), "Total", JOptionPane.INFORMATION_MESSAGE);
    }

    private void funcionarioMaisVelho() {
        JOptionPane.showMessageDialog(this, FuncionarioController.funcionarioMaisVelho(funcionarios), "Funcionário Mais Velho", JOptionPane.INFORMATION_MESSAGE);
    }

    private void calcularSalariosMinimos() {
        JOptionPane.showMessageDialog(this, FuncionarioController.calcularSalariosMinimos(funcionarios), "Salários Mínimos", JOptionPane.INFORMATION_MESSAGE);
    }

    private void agruparPorFuncao() {
        String resultado = FuncionarioController.agruparPorFuncao(funcionarios);
        JOptionPane.showMessageDialog(this, resultado, "Agrupamento por Função", JOptionPane.INFORMATION_MESSAGE);
    }

    private void criarPainelBotoes() {
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout());

        JButton btnRemover = new JButton("Remover João");
        btnRemover.addActionListener(e -> removerFuncionario(funcionarios, "João"));

        JButton btnAumentarSalario = new JButton("Aumentar Salário 10%");
        btnAumentarSalario.addActionListener(e -> aumentarSalario());

        JButton btnOrdenar = new JButton("Ordenar por Nome");
        btnOrdenar.addActionListener(e -> ordenarFuncionarios());

        JButton btnAniversariantes = new JButton("Aniversário (Out/Dez)");
        btnAniversariantes.addActionListener(e -> exibirAniversariantes());

        JButton btnTotalSalarios = new JButton("Total dos Salários");
        btnTotalSalarios.addActionListener(e -> calcularTotalSalarios());

        JButton btnFuncionarioMaisVelho = new JButton("Funcionário Mais Velho");
        btnFuncionarioMaisVelho.addActionListener(e -> funcionarioMaisVelho());

        JButton btnSalariosMinimos = new JButton("Salários Mínimos");
        btnSalariosMinimos.addActionListener(e -> calcularSalariosMinimos());

        JButton btnPopularTabela = new JButton("Popular Tabela / Restaurar");
        btnPopularTabela.addActionListener(e -> {
            carregarFuncionarios();
            atualizarTabela();
        });

        JButton btnAgruparPorFuncao = new JButton("Agrupar por Função");
        btnAgruparPorFuncao.addActionListener(e -> agruparPorFuncao());

        painelBotoes.add(btnPopularTabela);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnAgruparPorFuncao);
        painelBotoes.add(btnAumentarSalario);
        painelBotoes.add(btnOrdenar);
        painelBotoes.add(btnAniversariantes);
        painelBotoes.add(btnTotalSalarios);
        painelBotoes.add(btnFuncionarioMaisVelho);
        painelBotoes.add(btnSalariosMinimos);

        add(painelBotoes, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JanelaPrincipal janela = new JanelaPrincipal();
            janela.setVisible(true);
        });
    }
}
