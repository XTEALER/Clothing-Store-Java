
import javax.swing.*;

public class App 
{
    public static void main(String[] args) 
    {   
        
        int KeepAlive = 0, SelectedOpt, LoggedIn = 0, ActController = 0;
        String[] OpUser = {"Terminar Dia", "Ventas", "Inventario", "Cerrar Sesion"};
        
        do 
        {
            // inicio de sesion
            while(true) 
            {
                LoggedIn = Methods.vendedor();
                if(LoggedIn == 1)
                {
                    System.out.println("Inicio de sesion completado.");
                    break;
                } else if(LoggedIn == 0)
                {
                    System.out.println("Inicio de sesion fallido.");
                } else
                {
                    break;
                }
            }

            // ejecucion del programa de ventas
            while(LoggedIn != -1)
            {
                SelectedOpt = JOptionPane.showOptionDialog(null,
                "Textiles EL COSTOSO", "Bienvenido " + Methods.ArgID, 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.INFORMATION_MESSAGE,
                null, 
                OpUser,
                null);

                if(SelectedOpt == 3 || SelectedOpt == -1) 
                {
                    LoggedIn = -1;
                }
                else if(SelectedOpt == 0)
                {
                    System.out.println("Total de Ventas Durante el Dia: " + Methods.ventaDia);
                    Methods.stock(0.00, true);
                }
                else if(SelectedOpt == 1)
                {
                    do
                    {
                        if((Methods.DiasTotales % 8) == 0 && (Methods.inversion == 0))
                        {
                            while(true)
                            {
                                if(Methods.inversion == 0)
                                {
                                    Methods.inversiones();
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(null, "LA INVERSION NO PUEDE SER 0!", "ERROR", JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                            }
                        }
                        ActController = Methods.Ventas();
                    }
                    while(ActController == 1);
                }
                else if(SelectedOpt == 2)
                {
                    Methods.Inventario();
                }
            }

            // control de ejecucion programa principal
            KeepAlive = JOptionPane.showConfirmDialog(null, "Desea Cerrar El Programa?", "Control de Ejecucion", JOptionPane.YES_NO_OPTION);
        } while(KeepAlive == 1 && KeepAlive != -1);
        Methods.R.reader.close();
        System.out.println("\nTurning OFF program: " + KeepAlive);
        System.exit(0);
    }
}
