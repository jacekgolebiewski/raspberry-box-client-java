package pl.raspberry.box.app.jgolebiewski;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SwingTempExample extends JFrame implements KeyListener
{
    JTextPane j;

    public SwingTempExample()
    {
        super ("Iot game");
        setBounds (0, 0, 1, 1);
        setDefaultCloseOperation (EXIT_ON_CLOSE);
        /*
        j = new JTextPane ();
        j.setBackground(Color.BLACK);
        j.setForeground(Color.LIGHT_GRAY);
        j.setFont(new Font("courier", Font.BOLD, 15));
        j.addKeyListener (this);
        getContentPane ().add (j);
        */
        setVisible (true);
    }
    public void keyPressed (KeyEvent arg0)
    {
        // skip it, just need to implement it

    }

    public void keyReleased (KeyEvent arg0)
    {
        // skip it, just need to implement it
    }

    public void keyTyped (KeyEvent arg0)
    {
        char c = arg0.getKeyChar ();
        // do your stuff;
        print ((char) (c + 1) + " ");
    }
    private void print (String s)
    {
        System.out.println(s);
    }

    public static void main (String[] args)
    {
        new SwingTempExample();

    }

}

