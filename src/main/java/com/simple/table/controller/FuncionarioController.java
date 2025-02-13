package com.simple.table.controller;

import com.simple.table.model.Funcionario;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FuncionarioController {

    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");
    private static final DecimalFormat FORMATADOR_SALARIO = new DecimalFormat("#,##0.00");

    public static void removerFuncionario(List<Funcionario> funcionarios, String nome) {
        funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase(nome));
    }

    public static void aumentarSalario(List<Funcionario> funcionarios) {
        funcionarios.forEach(f -> f.setSalario(f.getSalario().multiply(new BigDecimal("1.10"))));
    }

    public static void ordenarFuncionarios(List<Funcionario> funcionarios) {
        funcionarios.sort(Comparator.comparing(Funcionario::getNome, String.CASE_INSENSITIVE_ORDER));
    }

    public static String exibirAniversariantes(List<Funcionario> funcionarios) {
        List<Funcionario> outubro = funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 10)
                .collect(Collectors.toList());

        List<Funcionario> dezembro = funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 12)
                .collect(Collectors.toList());

        StringBuilder resultado = new StringBuilder("Aniversariantes:\n");

        resultado.append("\n Outubro:\n ");
        if (outubro.isEmpty()) {
            resultado.append("Nenhum funcionário faz aniversário em Outubro.");
        } else {
            resultado.append(outubro.stream()
                    .map(f -> f.getNome() + " (" + f.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ")")
                    .collect(Collectors.joining(", ")));
        }

        resultado.append("\n Dezembro:\n ");
        if (dezembro.isEmpty()) {
            resultado.append("Nenhum funcionário faz aniversário em Dezembro.");
        } else {
            resultado.append(dezembro.stream()
                    .map(f -> f.getNome() + " (" + f.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ")")
                    .collect(Collectors.joining(", ")));
        }

        return resultado.toString();
    }

    public static String calcularTotalSalarios(List<Funcionario> funcionarios) {
        BigDecimal total = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return "Total dos Salários: R$ " + FORMATADOR_SALARIO.format(total);
    }

    public static String funcionarioMaisVelho(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .map(f -> "Nome: " + f.getNome() + "\nIdade: " + (java.time.LocalDate.now().getYear() - f.getDataNascimento().getYear()) + " anos")
                .orElse("Nenhum funcionário encontrado.");
    }

    public static String calcularSalariosMinimos(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .map(f -> f.getNome() + ": " + f.getSalario().divide(SALARIO_MINIMO, 2, BigDecimal.ROUND_HALF_UP) + " salários mínimos")
                .collect(Collectors.joining("\n"));
    }

    public static String agruparPorFuncao(List<Funcionario> funcionarios) {
        Map<String, List<Funcionario>> agrupado = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        StringBuilder resultado = new StringBuilder("Funcionários agrupados por função:\n");

        for (Map.Entry<String, List<Funcionario>> entry : agrupado.entrySet()) {
            resultado.append("\n").append(entry.getKey()).append(": ");
            resultado.append(entry.getValue().stream()
                    .map(Funcionario::getNome)
                    .collect(Collectors.joining(", ")));
        }

        return resultado.toString();
    }

}
