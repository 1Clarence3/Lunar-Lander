import java.awt.*;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.util.*;
import javax.swing.*;
import javax.swing.text.html.ImageView;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;

import javafx.scene.Group;
import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.animation.*;

public class Runner{

    //public int finished = 0;

    public static void main(String [] args) throws Exception
    {
        //launch(args);



        StdDraw.setCanvasSize(600,600);
        StdDraw.setXscale(0,100);
        StdDraw.setYscale(0,100);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.enableDoubleBuffering();

        System.out.println("Note: Due to the way the crossover and mutation algorithms work, if by around 50 generations no rockets have landed, restarting the program might be faster" +
                           "than waiting for more generations. (By that point, almost all rockets have same fitness, hard to mutate to correct genes)");

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        ArrayList<LunarModule> rockets = new ArrayList<LunarModule>();
        for(int i = 0; i < 15; i++)
        {
            rockets.add(new LunarModule(5+i*(90.0/14), rockets,i));
        }
        for(int i = 0; i < 15; i++)
        {
            rockets.get(i).boost(rockets.get(i).getGenes().get(0));
        }

        /*

        ArrayList<Integer> nums = new ArrayList<>();
        nums.add(1);
        nums.add(2);
        nums.clear();
        nums.add(4);
        nums.add(5);
        nums.set(0,nums.get(0)*2);
        nums.set(1,nums.get(1)*2);
        System.out.println(nums.get(0));
        System.out.println(nums.get(1));
        */
        /*
        int tempSum = 0;
        for(int i = 0; i < 15; i++)
        {
            if(rockets.get(i).getGenerationFinished())
            {
                tempSum++;
            }
        }
        if(tempSum == 15)
        {
            System.out.println("here");
            for(int i = 0; i < 15; i++)
            {
                rockets.get(i).setGenerationFinished(false);
                rockets.get(i).setPos(50);
            }
            for(int i = 0; i < 15; i++)
            {
                rockets.get(i).boost((int)(Math.random()*101));
            }
        }
        */


        /*
        while(true)
        {
            //rocket.update();
        }
        */
    }

    /*
    public void start(Stage primaryStage)throws Exception
    {
        primaryStage.setFullScreen(false);
        Group root = new Group();
        Scene scene = new Scene(root,700,700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lunar lander");
        Line base = new Line(0,500,700,500);
        root.getChildren().addAll(base);
        primaryStage.show();

        ArrayList<LunarModule> rockets = new ArrayList<LunarModule>();
        for(int i = 0; i < 15; i++)
        {
            rockets.add(new LunarModule(30+i*(650.0/14), rockets, i));
        }
        for(int i = 0; i < 15; i ++)
        {
            root.getChildren().addAll(rockets.get(i).getRocketImage());
            root.getChildren().addAll(rockets.get(i).getFlameImage());
            root.getChildren().addAll(rockets.get(i).getExplosionImage());
        }
        for(int i = 0; i < 15; i++)
        {
            rockets.get(i).boost((int)(Math.random()*101));
        }
    }
    */
}
