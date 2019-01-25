package AINT255;

import evaluator.ServerCommunication;

public class SimulationFrame extends javax.swing.JFrame {

    public static final int numberSimulationParameters = 22;

    private int simulationTime;

    private ServerCommunication server;

    private SimpleGA ga;

    public SimulationFrame() {
        initComponents();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        evolutionButton = new javax.swing.JButton();
        saveStatsButton = new javax.swing.JButton();
        fileNameTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        evolutionButton.setText("Start Evolution");
        evolutionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                evolutionButtonActionPerformed(evt);
            }
        });

        saveStatsButton.setText("Save Stats");
        saveStatsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveStatsButtonActionPerformed(evt);
            }
        });

        fileNameTextField.setText("evolutionResults");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(evolutionButton)
                    .addComponent(saveStatsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(evolutionButton)
                .addGap(47, 47, 47)
                .addComponent(saveStatsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fileNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void evolutionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_evolutionButtonActionPerformed

        // assume all the parameters are being evolved. 
        int numberParamsToEvolve = 22;

        // decide on core GA parameters
        int populationSize = 20;

        int numGenerations = 48;
        double probabilityCrossover = 0.7;

        double mutationMaginitude = 0.02;

        // the simulation is fixed at 1,000,000 game �tics�. 
        // So to ensure the simulation does not time out you need to set the simtime, 
        // populationSize and numberGenerations parameters so that the 1,000,000 game �tics� is  not exceeded. 
        // In other words the constraint is:  simulationTime x  populationSize x numberGenerations  < 1,000,000
        simulationTime = 1000;

        server = new ServerCommunication("localhost", 3001);
        server.setFitnessFunction(new FitnessMeasure());

        // now create the GA with these parameters
        ga = new SimpleGA(numberParamsToEvolve, populationSize, numGenerations, probabilityCrossover, mutationMaginitude, server, simulationTime);

        // finally evolve
        ga.evolvePopulation();

    }//GEN-LAST:event_evolutionButtonActionPerformed

    private void saveStatsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveStatsButtonActionPerformed

        ga.saveEvolutionStats(fileNameTextField.getText() + ".txt");

    }//GEN-LAST:event_saveStatsButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SimulationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SimulationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SimulationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SimulationFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SimulationFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton evolutionButton;
    private javax.swing.JTextField fileNameTextField;
    private javax.swing.JButton saveStatsButton;
    // End of variables declaration//GEN-END:variables
}
