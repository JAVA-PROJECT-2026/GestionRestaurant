/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.view.panel;

import main.controller.StatistiqueController;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Panel principal du dashboard statistiques du restaurant.
 * Affiche des KPI, des courbes de CA et des histogrammes.
 *
 * Dépendance requise : JFreeChart 1.5.x
 * Télécharger le JAR sur https://sourceforge.net/projects/jfreechart/
 * ou ajouter dans pom.xml :
 *
 * @author isaac
 */
public class StatistiquePanel extends JPanel {

    // ── Palette de couleurs ────────────────────────────────────────────────────
    private static final Color BG_DARK        = new Color(18, 18, 35);
    private static final Color BG_CARD        = new Color(28, 28, 50);
    private static final Color BG_CHART       = new Color(22, 22, 42);
    private static final Color ACCENT_PURPLE  = new Color(130, 80, 255);
    private static final Color ACCENT_TEAL    = new Color(0, 200, 180);
    private static final Color ACCENT_ORANGE  = new Color(255, 140, 60);
    private static final Color ACCENT_RED     = new Color(255, 75, 100);
    private static final Color ACCENT_GREEN   = new Color(60, 220, 120);
    private static final Color TEXT_MAIN      = new Color(240, 240, 255);
    private static final Color TEXT_SUB       = new Color(150, 150, 180);
    private static final Color GRID_COLOR     = new Color(40, 40, 65);

    private static final Font FONT_TITLE  = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FONT_KPI    = new Font("Segoe UI", Font.BOLD, 30);
    private static final Font FONT_LABEL  = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 14);

    private final StatistiqueController controller;
    private final DecimalFormat dfMoney = new DecimalFormat("#,##0.00 FCFA");
    private final DecimalFormat dfNum   = new DecimalFormat("#,##0");

    // ──────────────────────────────────────────────────────────────────────────

    public StatistiquePanel() {
        this.controller = new StatistiqueController();
        setLayout(new BorderLayout());
        setBackground(BG_DARK);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(buildHeader(),   BorderLayout.NORTH);
        add(buildContent(),  BorderLayout.CENTER);
    }

    // =========================================================
    //  HEADER
    // =========================================================
    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel title = new JLabel("📊  Dashboard Statistiques");
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT_MAIN);

        String dateStr = new SimpleDateFormat("dd MMMM yyyy").format(new Date());
        JLabel dateLbl = new JLabel(dateStr);
        dateLbl.setFont(FONT_LABEL);
        dateLbl.setForeground(TEXT_SUB);

        JButton refreshBtn = buildButton("⟳  Actualiser");
        refreshBtn.addActionListener(e -> refresh());

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);
        right.add(dateLbl);
        right.add(refreshBtn);

        header.add(title, BorderLayout.WEST);
        header.add(right,  BorderLayout.EAST);
        return header;
    }

    // =========================================================
    //  CONTENU PRINCIPAL (scroll)
    // =========================================================
    private JScrollPane buildContent() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);

        // 1. Ligne KPI
        content.add(buildKPIRow());
        content.add(Box.createVerticalStrut(20));

        // 2. Courbes (CA + nb commandes)
        content.add(buildChartsRow(
            buildCourbeCA(),
            buildCourbeCommandes()
        ));
        content.add(Box.createVerticalStrut(20));

        // 3. Histogrammes (top produits + états + catégories)
        content.add(buildChartsRow(
            buildHistoTopProduits(),
            buildHistoEtats()
        ));
        content.add(Box.createVerticalStrut(20));

        content.add(buildSingleChartRow(buildHistoCategories()));
        content.add(Box.createVerticalStrut(20));

        JScrollPane scroll = new JScrollPane(content);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    // =========================================================
    //  LIGNE DE KPI
    // =========================================================
    private JPanel buildKPIRow() {
        JPanel row = new JPanel(new GridLayout(1, 5, 15, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        row.add(buildKPICard("  CA Total",
            dfMoney.format(controller.getCATotal()),
            ACCENT_PURPLE));

        row.add(buildKPICard("  Cmd. Validées",
            dfNum.format(controller.getNbCommandesValidees()),
            ACCENT_TEAL));

        row.add(buildKPICard("  Panier Moyen",
            dfMoney.format(controller.getPanierMoyen()),
            ACCENT_GREEN));

        row.add(buildKPICard("  Produits",
            dfNum.format(controller.getNbProduits()),
            ACCENT_ORANGE));

        row.add(buildKPICard("  Alertes Stock",
            dfNum.format(controller.getNbProduitsEnAlerte()),
            ACCENT_RED));

        return row;
    }

    private JPanel buildKPICard(String label, String value, Color accent) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, accent),
            new EmptyBorder(15, 18, 15, 12)
        ));

        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(TEXT_SUB);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel val = new JLabel(value);
        val.setFont(FONT_KPI);
        val.setForeground(TEXT_MAIN);
        val.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(lbl);
        card.add(Box.createVerticalStrut(6));
        card.add(val);
        return card;
    }

    // =========================================================
    //  COURBE : CA PAR JOUR
    // =========================================================
    private ChartPanel buildCourbeCA() {
        Map<String, Double> data = controller.getCAParJour();
        XYSeries series = new XYSeries("CA (FCFA)");
        int i = 0;
        for (Map.Entry<String, Double> e : data.entrySet()) {
            series.add(i++, e.getValue());
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
            "Chiffre d'Affaires — 30 derniers jours",
            "Jours", "CA (FCFA)",
            dataset, PlotOrientation.VERTICAL,
            false, true, false
        );
        styleLineChart(chart, ACCENT_PURPLE, data);
        return wrapChart(chart, 460, 280);
    }

    // =========================================================
    //  COURBE : NOMBRE DE COMMANDES PAR JOUR
    // =========================================================
    private ChartPanel buildCourbeCommandes() {
        Map<String, Integer> data = controller.getNbCommandesParJour();
        XYSeries series = new XYSeries("Commandes");
        int i = 0;
        for (Integer v : data.values()) {
            series.add(i++, v);
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
            "Commandes validées — 30 derniers jours",
            "Jours", "Nb commandes",
            dataset, PlotOrientation.VERTICAL,
            false, true, false
        );
        // Convertir data keys pour passer en Map<String, Double>
        Map<String, Double> dataD = new java.util.LinkedHashMap<>();
        for (Map.Entry<String, Integer> e : data.entrySet()) {
            dataD.put(e.getKey(), e.getValue().doubleValue());
        }
        styleLineChart(chart, ACCENT_TEAL, dataD);
        return wrapChart(chart, 460, 280);
    }

    // =========================================================
    //  HISTO : TOP 5 PRODUITS
    // =========================================================
    private ChartPanel buildHistoTopProduits() {
        Map<String, Integer> data = controller.getTopProduits(5);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Integer> e : data.entrySet()) {
            dataset.addValue(e.getValue(), "Qté vendue", truncate(e.getKey(), 18));
        }

        JFreeChart chart = ChartFactory.createBarChart(
            "Top 5 Produits les plus vendus",
            "", "Quantités vendues",
            dataset, PlotOrientation.HORIZONTAL,
            false, true, false
        );
        styleBarChart(chart, dataset,
            new Color[]{ACCENT_PURPLE, ACCENT_TEAL, ACCENT_GREEN, ACCENT_ORANGE, ACCENT_RED});
        return wrapChart(chart, 460, 280);
    }

    // =========================================================
    //  HISTO : COMMANDES PAR ÉTAT
    // =========================================================
    private ChartPanel buildHistoEtats() {
        Map<String, Integer> data = controller.getCommandesParEtat();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<String, Color> colors = new java.util.LinkedHashMap<>();
        colors.put("EN_COURS", ACCENT_ORANGE);
        colors.put("VALIDEE",  ACCENT_GREEN);
        colors.put("ANNULEE",  ACCENT_RED);

        for (Map.Entry<String, Integer> e : data.entrySet()) {
            dataset.addValue(e.getValue(), e.getKey(), e.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
            "Répartition des commandes par état",
            "État", "Nombre",
            dataset, PlotOrientation.VERTICAL,
            false, true, false
        );

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        applyPlotStyle(plot);
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardBarPainter());
        renderer.setMaximumBarWidth(0.3);
        renderer.setShadowVisible(false);

        int idx = 0;
        for (Map.Entry<String, Color> e : colors.entrySet()) {
            if (data.containsKey(e.getKey())) {
                renderer.setSeriesPaint(idx++, e.getValue());
            }
        }
        // Un dataset à valeur unique par catégorie : couleur par item
        for (int i = 0; i < dataset.getColumnCount(); i++) {
            String key = (String) dataset.getColumnKey(i);
            Color c = colors.getOrDefault(key, ACCENT_PURPLE);
            renderer.setSeriesPaint(i, c);
        }

        return wrapChart(chart, 460, 280);
    }

    // =========================================================
    //  HISTO : PRODUITS PAR CATÉGORIE
    // =========================================================
    private ChartPanel buildHistoCategories() {
        Map<String, Integer> data = controller.getProduitsParCategorie();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Integer> e : data.entrySet()) {
            dataset.addValue(e.getValue(), "Produits", truncate(e.getKey(), 20));
        }

        JFreeChart chart = ChartFactory.createBarChart(
            "Nombre de produits par catégorie",
            "Catégorie", "Nb produits",
            dataset, PlotOrientation.VERTICAL,
            false, true, false
        );

        Color[] palette = {ACCENT_PURPLE, ACCENT_TEAL, ACCENT_ORANGE, ACCENT_GREEN,
                           ACCENT_RED, new Color(100, 160, 255)};
        styleBarChart(chart, dataset, palette);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis axis = plot.getDomainAxis();
        axis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        return wrapChart(chart, Integer.MAX_VALUE, 300);
    }

    // =========================================================
    //  HELPERS : STYLES
    // =========================================================
    private void styleLineChart(JFreeChart chart, Color lineColor, Map<String, Double> labels) {
        applyChartGlobalStyle(chart);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(BG_CHART);
        plot.setDomainGridlinePaint(GRID_COLOR);
        plot.setRangeGridlinePaint(GRID_COLOR);
        plot.setOutlineVisible(false);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(true);

        // Axe X : afficher les vraies dates (1 sur 5)
        NumberAxis domainAxis = new NumberAxis();
        domainAxis.setTickLabelFont(FONT_LABEL);
        domainAxis.setTickLabelPaint(TEXT_SUB);
        domainAxis.setAxisLinePaint(GRID_COLOR);
        domainAxis.setVisible(false);
        plot.setDomainAxis(domainAxis);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setTickLabelFont(FONT_LABEL);
        rangeAxis.setTickLabelPaint(TEXT_SUB);
        rangeAxis.setAxisLinePaint(GRID_COLOR);
        rangeAxis.setLabelFont(FONT_LABEL);
        rangeAxis.setLabelPaint(TEXT_SUB);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, lineColor);
        renderer.setSeriesStroke(0, new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        renderer.setSeriesShapesVisible(0, false);
        // Remplissage sous la courbe
        renderer.setSeriesFillPaint(0, new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue(), 30));
        plot.setRenderer(renderer);
    }

    private void styleBarChart(JFreeChart chart, DefaultCategoryDataset dataset, Color[] palette) {
        applyChartGlobalStyle(chart);

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        applyPlotStyle(plot);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardBarPainter());
        renderer.setShadowVisible(false);
        renderer.setMaximumBarWidth(0.5);

        for (int i = 0; i < dataset.getRowCount(); i++) {
            renderer.setSeriesPaint(i, palette[i % palette.length]);
        }
        // Couleur par item si une seule série
        if (dataset.getRowCount() == 1) {
            for (int i = 0; i < dataset.getColumnCount(); i++) {
                renderer.setSeriesPaint(i, palette[i % palette.length]);
            }
        }
    }

    private void applyPlotStyle(CategoryPlot plot) {
        plot.setBackgroundPaint(BG_CHART);
        plot.setDomainGridlinePaint(GRID_COLOR);
        plot.setRangeGridlinePaint(GRID_COLOR);
        plot.setOutlineVisible(false);

        CategoryAxis domain = plot.getDomainAxis();
        domain.setTickLabelFont(FONT_LABEL);
        domain.setTickLabelPaint(TEXT_SUB);
        domain.setAxisLinePaint(GRID_COLOR);
        domain.setLabelFont(FONT_LABEL);
        domain.setLabelPaint(TEXT_SUB);

        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setTickLabelFont(FONT_LABEL);
        range.setTickLabelPaint(TEXT_SUB);
        range.setAxisLinePaint(GRID_COLOR);
        range.setLabelFont(FONT_LABEL);
        range.setLabelPaint(TEXT_SUB);
    }

    private void applyChartGlobalStyle(JFreeChart chart) {
        chart.setBackgroundPaint(BG_CARD);
        chart.setBorderVisible(false);

        TextTitle title = chart.getTitle();
        if (title != null) {
            title.setFont(FONT_HEADER);
            title.setPaint(TEXT_MAIN);
            title.setPadding(12, 0, 8, 0);
        }
    }

    // =========================================================
    //  HELPERS : LAYOUT
    // =========================================================
    private JPanel buildChartsRow(ChartPanel left, ChartPanel right) {
        JPanel row = new JPanel(new GridLayout(1, 2, 15, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 310));
        row.add(wrapInCard(left));
        row.add(wrapInCard(right));
        return row;
    }

    private JPanel buildSingleChartRow(ChartPanel chart) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 330));
        row.add(wrapInCard(chart), BorderLayout.CENTER);
        return row;
    }

    private ChartPanel wrapChart(JFreeChart chart, int maxW, int prefH) {
        ChartPanel cp = new ChartPanel(chart);
        cp.setOpaque(false);
        cp.setPreferredSize(new Dimension(Math.min(maxW, 600), prefH));
        cp.setPopupMenu(null);
        cp.setMouseWheelEnabled(false);
        return cp;
    }

    private JPanel wrapInCard(JComponent comp) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GRID_COLOR, 1),
            new EmptyBorder(8, 8, 8, 8)
        ));
        card.add(comp, BorderLayout.CENTER);
        return card;
    }

    private JButton buildButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_LABEL);
        btn.setForeground(TEXT_MAIN);
        btn.setBackground(ACCENT_PURPLE);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));
        return btn;
    }

    private String truncate(String s, int max) {
        return s.length() > max ? s.substring(0, max - 1) + "…" : s;
    }

    // =========================================================
    //  RAFRAÎCHIR
    // =========================================================
    private void refresh() {
        removeAll();
        add(buildHeader(),  BorderLayout.NORTH);
        add(buildContent(), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // =========================================================
    //  MAIN DE TEST — pour tester en standalone
    // =========================================================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("📊 Dashboard — Restaurant");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 800);
            frame.setMinimumSize(new Dimension(900, 600));
            frame.setLocationRelativeTo(null);
            frame.getContentPane().setBackground(new Color(18, 18, 35));
            frame.add(new StatistiquePanel());
            frame.setVisible(true);
        });
    }
}