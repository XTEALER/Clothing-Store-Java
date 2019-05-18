
import javax.swing.*;

public class App 
{
    
    public static void main(String[] args) 
    {
        int KeepAlive = 0, SelectedOpt, LoggedIn = 0, ActController = 0;
        String[] OpUser = {"Opciones", "Ventas", "Inventario", "Cerrar Sesion"};
        
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

                }
                else if(SelectedOpt == 1)
                {
                    do
                    {
                        ActController = Methods.Ventas();
                    } 
                    while(ActController != -1 && ActController != 0 || ActController == -2);
                }
                else if(SelectedOpt == 2)
                {

                }
            }

            // control de ejecucion programa principal
            KeepAlive = JOptionPane.showConfirmDialog(null, "Desea Cerrar El Programa?", "Control de Ejecucion", JOptionPane.YES_NO_OPTION);
        } while(KeepAlive == 1 && KeepAlive != -1);
        System.out.println("\nTurning OFF program: " + KeepAlive);
        System.exit(0);
    }
}