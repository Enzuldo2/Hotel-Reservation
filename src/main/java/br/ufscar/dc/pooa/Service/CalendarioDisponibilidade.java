package br.ufscar.dc.pooa.Service;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


class CalendarioDisponibilidade {
    private final int totalQuartos;
    private final Map<Date, Integer> ocupacaoPorDia;

    public CalendarioDisponibilidade(int totalQuartos) {
        this.totalQuartos = totalQuartos;
        this.ocupacaoPorDia = new HashMap<>();
    }

    public void registrarReserva(Date data_inicial, Date data_fim) {
        Date data_fim_ajustada = new Date(data_fim.getTime() - 1); // Subtrai 1 milissegundo da data final
        Date data_inicial_ajustada = new Date(data_inicial.getTime() + 1); // Adiciona 1 milissegundo à data inicial
        Calendar cal = Calendar.getInstance();
        cal.setTime(data_inicial_ajustada);

        while (!cal.getTime().after(data_fim_ajustada)) {
            ocupacaoPorDia.put(data_inicial_ajustada, ocupacaoPorDia.getOrDefault(data_inicial_ajustada, 0) + 1);
            data_inicial_ajustada = new Date(data_inicial_ajustada.getTime() + 86400000); // Adiciona 1 dia à data
            cal.add(Calendar.DATE, 1);
        }
    }

    public boolean temDisponibilidade(Date data_inicial, Date data_fim) {
        Date data_fim_ajustada = new Date(data_fim.getTime() - 1); // Subtrai 1 milissegundo da data final
        Date data_inicial_ajustada = new Date(data_inicial.getTime() + 1); // Adiciona 1 milissegundo à data inicial
        Calendar cal = Calendar.getInstance();
        cal.setTime(data_inicial_ajustada);

        while (!cal.getTime().after(data_fim_ajustada)) {
            if (ocupacaoPorDia.getOrDefault(data_inicial_ajustada, 0) >= totalQuartos) {
                return false; // Não há quartos disponíveis para este dia
            }
            cal.add(Calendar.DATE, 1);
        }

        return true; // Há quartos disponíveis para todos os dias no intervalo
    }


}