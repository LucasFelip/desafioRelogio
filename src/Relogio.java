import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class Relogio extends JPanel {

    private static final int LARGURA = 400; // Largura do painel
    private static final int ALTURA = 400; // Altura do painel
    private static final int RAIO_HORAS = 60; // Raio para o ponteiro de horas (mais próximo do centro)
    private static final int RAIO_MINUTOS = 100; // Raio para o ponteiro de minutos
    private static final int RAIO_SEGUNDOS = 120; // Raio para o ponteiro de segundos

    public Relogio() {
        setPreferredSize(new Dimension(LARGURA, ALTURA)); // Define o tamanho do painel
        setBackground(Color.WHITE); // Define o fundo do relógio como branco

        // Configuração de um timer para atualizar o relógio a cada segundo
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                repaint(); // Re-renderiza o componente para atualizar a hora
            }
        }, 0, 1000);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Habilita suavização de bordas

        int centroX = LARGURA / 2; // Coordenada X do centro do relógio
        int centroY = ALTURA / 2; // Coordenada Y do centro do relógio

        // Obtém a hora atual do sistema
        LocalTime horaAtual = LocalTime.now();
        int horas = horaAtual.getHour() % 12; // Converte o formato de 24h para 12h
        int minutos = horaAtual.getMinute(); // Obtém os minutos
        int segundos = horaAtual.getSecond(); // Obtém os segundos

        // Desenha os ponteiros do relógio
        desenhaPonteiro(g2d, centroX, centroY, RAIO_HORAS, 3, horas, 12); // Ponteiro de horas
        desenhaPonteiro(g2d, centroX, centroY, RAIO_MINUTOS, 5, minutos, 60); // Ponteiro de minutos
        desenhaPonteiro(g2d, centroX, centroY, RAIO_SEGUNDOS, 6, segundos, 60); // Ponteiro de segundos
    }

    /**
     * Desenha um ponteiro do relógio composto por múltiplos números.
     *
     * @param g2d          Contexto gráfico
     * @param centroX      Coordenada X do centro
     * @param centroY      Coordenada Y do centro
     * @param raio         Raio base para o ponteiro
     * @param valoresNoCorpo Número de valores no corpo do ponteiro
     * @param valorAtual   Valor atual do ponteiro
     * @param maximoValor  Valor máximo (12 para horas, 60 para minutos/segundos)
     */
    private void desenhaPonteiro(Graphics2D g2d, int centroX, int centroY, int raio, int valoresNoCorpo, int valorAtual, int maximoValor) {
        g2d.setColor(Color.BLACK); // Define a cor do texto do ponteiro
        Font fonte = new Font("Arial", Font.BOLD, 14); // Define a fonte do texto
        g2d.setFont(fonte);

        // Calcula o ângulo do ponteiro com base no valor atual
        double anguloBase = Math.toRadians(-90); // Começa em -90 graus (posição das 12 horas)
        double incrementoAngulo = 2 * Math.PI / maximoValor; // Incremento do ângulo para cada unidade
        double anguloAtual = anguloBase + valorAtual * incrementoAngulo; // Posição angular do ponteiro

        // Desenha os números que compõem o corpo do ponteiro
        for (int i = 0; i < valoresNoCorpo; i++) {
            String texto = String.format("%02d", valorAtual); // Converte o valor atual para texto no formato "00"

            // Calcula as coordenadas para o número com base no raio ajustado
            int x = (int) (centroX + Math.cos(anguloAtual) * (raio - i * 20));
            int y = (int) (centroY + Math.sin(anguloAtual) * (raio - i * 20));

            g2d.drawString(texto, x - 10, y + 5); // Desenha o número ajustado ao centro do texto
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Relógio");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha o programa ao fechar a janela
            frame.add(new Relogio()); // Adiciona o painel do relógio à janela
            frame.pack(); // Ajusta o tamanho da janela ao conteúdo
            frame.setLocationRelativeTo(null); // Centraliza a janela na tela
            frame.setVisible(true); // Torna a janela visível
        });
    }
}
