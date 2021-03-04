import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.applet.Applet;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.FileInputStream;
import java.text.AttributedCharacterIterator;
import java.util.*;
import java.util.Timer;
import javax.swing.*;

public class LunarModule {
    private double pos, velocity, acceleration, x, time, fitness, tempTime;
    private int fuel, state, terminalVelocity;
    private Timer timer = new Timer();
    private Timer timerPause = new Timer();
    private ImageIcon icon;
    private int finished = 0;
    private ArrayList<LunarModule> rockets;
    private int num;
    private boolean generationFinished, landed;
    private ImageView rocketImage, flameImage, explosionImage;
    private Image image = new Image(new FileInputStream("LunarModule.png"));
    private ArrayList<Double> genes = new ArrayList<Double>();
    private ArrayList<Double> genesCopy = new ArrayList<Double>();
    private LunarModule parent1, parent2;
    private int generation = 0;
    private int generatedPopulation;
    private double mutation1 = 10.0;
    private double mutation2 = 110.0;
    private double mutation3 = 40.0;


    public LunarModule(double x1, ArrayList<LunarModule> rockets1, int index) throws Exception
    {
        generatedPopulation = 0;
        rockets = rockets1;
        fuel = 900;
        velocity = 0;
        acceleration = 0;
        pos = 50;
        state = 1; //Stationary
        terminalVelocity = 50;
        time = 0;
        x = x1;
        //StdDraw.picture(x,50,"LunarModule.png");
        Font font = new Font("Sans Serif", Font.BOLD, 12);
        //StdDraw.setFont(font);
        //StdDraw.text(10,90,"Fuel: " + fuel);
        //Population test = new Population();
        num = index;
        rocketImage = new ImageView(image);
        flameImage = new ImageView(new Image(new FileInputStream("Flame.png")));
        explosionImage = new ImageView(new Image(new FileInputStream("Explosion.png")));
        for(int i = 0; i < 30; i++)
        {
            if(i%2 == 0)
            {
                genes.add((Math.random()*41));
            }
            else
            {
                genes.add((Math.random()*81));
            }
        }

        for(int i = 0; i < genes.size(); i++)
        {
            genesCopy.add(genes.get(i));
        }
        landed = false;
    }

    public ArrayList<Double> getGenes()
    {
        return genes;
    }

    public boolean getGenerationFinished()
    {
        return generationFinished;
    }

    public ImageView getRocketImage()
    {
        return rocketImage;
    }

    public ImageView getFlameImage()
    {
        return flameImage;
    }

    public ImageView getExplosionImage()
    {
        return explosionImage;
    }

    public int getFuel()
    {
        return fuel;
    }

    public void setFuel(int currFuel)
    {
        fuel = currFuel;
    }

    public void setState(int currState)
    {
        state = currState;
    }

    public void setVelocity(double currVelocity)
    {
        velocity = currVelocity;
    }

    public void setPos(double currPos)
    {
        pos = currPos;
    }

    public double getPos()
    {
        return pos;
    }

    public double getVelocity()
    {
        return velocity;
    }

    public int getState()
    {
        return state;
    }

    public void boost(double millis) throws Exception
    {
        StdDraw.picture(50, 7, "Line.JPG");
        timer = new Timer("Boost");
        TimerTask task = new TimerTask() {
            public void run() {
                if (time > millis) {
                    if(genes.size() > 1)
                    {
                        time = 0;
                        genes.remove(0);
                        timer.cancel();
                        noBoost(genes.get(0));
                    }
                    else
                    {
                        time = 0;
                        timer.cancel();
                        noBoost((int)(Math.random()*81));
                    }
                }
                /*
                int x = (int)(Math.random()*(50)+30);
                int y = (int)(Math.random()*(50)+30);
                //Image image = icon.getImage().getScaledInstance(x,y, Image.SCALE_SMOOTH);
                Component c = new Component() {
                };
                */
                //StdDraw.clear();
               // StdDraw.setPenColor(Color.BLACK);
                if(pos > 90)
                {
                    setFuel(0);
                    setState(2);
                    setVelocity(0);
                }
                if (fuel != 0)
                {
                    if (pos <= 10)
                    {
                        if(getState() != 0)
                        {
                            setState(0);
                            if (Math.abs(getVelocity()) >= 0.3) {
                                fitness = Math.abs(getVelocity());
                                //explosionImage.setX(x);
                                //explosionImage.setY(500);
                                StdDraw.picture(x, 10, "Explosion.png");
                                setFuel(900);
                            } else {
                                //StdDraw.text(x,50, "Landed successfully!");
                                landed = true;
                                fitness = Math.abs(getVelocity());
                                setFuel(900);
                            }
                            StdDraw.show(1000);
                            StdDraw.setPenColor(StdDraw.WHITE);
                            StdDraw.filledRectangle(x, pos, 2, 8);
                            StdDraw.picture(50, 7, "Line.JPG");
                            setVelocity(0);
                            finished = 1;
                            int sum = 0;
                            for (int i = 0; i < rockets.size(); i++) {
                                sum += rockets.get(i).isFinished();
                            }
                            if (sum == 15) {
                                for (int i = 0; i < rockets.size(); i++) {
                                    rockets.get(i).finished = 0;
                                }
                                for (int i = 0; i < rockets.size(); i++) {
                                    rockets.get(i).setPos(50);
                                    rockets.get(i).setState(1);
                                }
                                StdDraw.setPenColor(Color.WHITE);
                                StdDraw.filledRectangle(50, 15, 50, 15);
                                //StdDraw.setPenColor(Color.BLACK);
                                generationFinished = true;
                                timer.cancel();
                                //produceGeneration();
                                StdDraw.pause(1000);
                                try
                                {
                                    noBoost((int) (Math.random() * 81));
                                    //genes.remove(0);
                                    //noBoost(genes.get(0));
                                }
                                catch(Exception e)
                                {
                                    System.out.println("Error: noboost");
                                }
                            }
                        }
                    }
                    else
                    {
                        if(getState() != 0)
                        {
                            StdDraw.setPenColor(Color.WHITE);
                            StdDraw.filledRectangle(x, pos, 2, 8);
                            //StdDraw.setPenColor(Color.BLACK);
                            //StdDraw.setPenRadius(0.02);
                            //StdDraw.line(0, 5, 100, 5);
                            //StdDraw.setPenRadius();
                            //StdDraw.text(x, pos - 10, "Fuel: " + getFuel());
                            //StdDraw.text(x, pos - 15, "Altitude: " + (int) (getPos()));
                            StdDraw.picture(x, getPos(), "LunarModule.png");
                            StdDraw.picture(x, getPos() - 5, "Flame.png");
                            //rocketImage.setX(x);
                            //rocketImage.setY(getPos());
                            //flameImage.setX(x);
                            //flameImage.setY(getPos()+5);
                            setState(1); //Upwards
                            setVelocity(getVelocity() + 0.01 - 0.005);
                            setPos(getPos() + getVelocity());
                            setFuel(getFuel() - 1);
                            StdDraw.pause(20);
                            time++;
                            fuel--;
                            StdDraw.show();
                        }
                    }
                }
                else
                {
                    timer.cancel();
                    time = 0;
                    noBoost((int) (Math.random() * 81));
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0,1);
    }

    public void noBoost(double millis)
    {
        int tempreset = 0;
        for(int i = 0; i < rockets.size(); i++)
        {
            if(rockets.get(i).pos > 10)
            {
                tempreset++;
            }
        }
        if(tempreset == 15 )
        {
            for(int i = 0; i < rockets.size(); i++)
            {
                rockets.get(i).generatedPopulation = 0;
            }
        }
        StdDraw.picture(50, 7, "Line.JPG");
        timer = new Timer("NoBoost");
        TimerTask task = new TimerTask() {
            public void run() {
                if(time > millis)
                {
                    if(genes.size() > 1)
                    {
                        time = 0;
                        genes.remove(0);
                        timer.cancel();
                        try
                        {
                            boost(genes.get(0));
                        }
                        catch(Exception e)
                        {
                            System.out.println("Error: boost");
                        }
                    }
                    else
                    {
                        time = 0;
                        timer.cancel();
                        try
                        {
                            boost((int)(Math.random()*41));
                        }
                        catch(Exception e)
                        {
                            System.out.println("Error: boost");
                        }
                    }
                }
                if(pos > 90)
                {
                    setFuel(0);
                    setState(2);
                    setVelocity(0);
                }
                //StdDraw.clear();

                //StdDraw.setPenColor(Color.BLACK);
                //StdDraw.setPenRadius(0.02);
                //StdDraw.line( 0,5 ,100 ,5 );
                //StdDraw.setPenRadius();
                //StdDraw.text(x,pos-10,"Fuel: " + getFuel());
                //StdDraw.text(x,pos-15,"Altitude: " + (int)(getPos()));

                //rocketImage.setX(x);
                //rocketImage.setY(getPos());
                if(getState() == 1 || getState() == 2)
                {
                    StdDraw.setPenColor(Color.WHITE);
                    StdDraw.filledRectangle(x,pos, 2, 8);
                    StdDraw.picture(x,getPos(),"LunarModule.png");
                    if(getPos() > 10)
                    {
                        setState(2); //Falling
                        setVelocity(getVelocity()-0.005);
                        setPos(getPos()+getVelocity());
                        StdDraw.pause(20);
                    }
                    else
                    {
                        setState(0);
                        if(Math.abs(getVelocity()) >= 0.3)
                        {
                            fitness = Math.abs(getVelocity());
                            //explosionImage.setX(x);
                            //explosionImage.setY(500);
                            StdDraw.picture(x,10,"Explosion.png");
                            setFuel(900);
                        }
                        else
                        {
                            //StdDraw.text(x,50, "Landed successfully!");
                            landed = true;
                            fitness = Math.abs(getVelocity());
                            setFuel(900);
                        }
                        StdDraw.show(1000);
                        StdDraw.setPenColor(StdDraw.WHITE);
                        StdDraw.filledRectangle(x, pos, 2, 8);
                        StdDraw.picture(50, 7, "Line.JPG");
                        setVelocity(0);
                        finished = 1;
                        int sum = 0;
                        for(int i = 0; i < rockets.size(); i++)
                        {
                            sum += rockets.get(i).isFinished();
                        }
                        if(sum == 15)
                        {
                            for(int i = 0; i < rockets.size(); i++)
                            {
                                rockets.get(i).finished = 0;
                            }
                            for(int i = 0; i < rockets.size(); i++)
                            {
                                rockets.get(i).setPos(50);
                                rockets.get(i).setState(1);
                            }
                            StdDraw.setPenColor(Color.WHITE);
                            StdDraw.filledRectangle(50, 15, 50, 15);
                            //StdDraw.setPenColor(Color.BLACK);
                            generationFinished = true;
                            timer.cancel();
                            if(generatedPopulation == 0)
                            {
                                produceGeneration();
                                for(int i = 0; i < rockets.size(); i++)
                                {
                                    rockets.get(i).generatedPopulation = 1;
                                }
                            }

                            System.out.println("Generation: " + generation);
                            int tempLandedSum = 0;
                            for(int i = 0; i < rockets.size(); i++)
                            {
                                if(rockets.get(i).landed)
                                {
                                    tempLandedSum++;
                                }
                            }
                            System.out.println("Rockets Landed Successfully: " + tempLandedSum);
                            for(int i = 0; i < rockets.size(); i++)
                            {
                                rockets.get(i).generation++;
                            }
                            if(generation%5 == 0)
                            {
                                StdDraw.clear();
                            }
                            StdDraw.pause(1000);
                            for(int i = 0; i < rockets.size(); i++)
                            {
                                rockets.get(i).landed = false;
                            }
                            try
                            {
                                boost(genes.get(0));
                                //genes.remove(0);
                                //boost(genes.get(0));
                            }
                            catch(Exception e)
                            {
                                System.out.println("Error: boost");
                            }
                        }
                    }
                }
                time++;
                StdDraw.show();
            }
        };
        timer.scheduleAtFixedRate(task, 0,1);
    }

    public void setGenerationFinished(boolean bool)
    {
        generationFinished = bool;
    }

    public double getFitness()
    {
        return fitness;
    }

    public int isFinished()
    {
        return finished;
    }

    public void produceGeneration()
    {
        ArrayList<Double> rankedFitness = new ArrayList<Double>();
        for(int i = 0; i < rockets.size(); i++)
        {
            rankedFitness.add(rockets.get(i).getFitness());
        }
        Collections.sort(rankedFitness);
        int parentIndex1 = 0, parentIndex2 = 0;
        int clone1Index = 0, clone2Index = 0, clone3Index = 0, clone4Index = 0;
        ArrayList<Double> parent1Genes = new ArrayList<>();
        ArrayList<Double> parent2Genes = new ArrayList<>();
        for(int i = 0; i < rockets.size(); i++)
        {
            if(rockets.get(i).getFitness() == rankedFitness.get(0))
            {
                parentIndex1 = i;
                for (int j = 0; j < rockets.get(i).genesCopy.size(); j++)
                {
                    parent1Genes.add(rockets.get(i).genesCopy.get(j));
                }
                i = rockets.size();
                //break;
                /*
                try
                {
                    parent1 = new LunarModule(rockets.get(i).x, rockets, rockets.get(i).num);
                    for(int j = 0; j < rockets.get(i).getGenes().size(); j++)
                    {
                        parent1.getGenes().add(rockets.get(i).getGenes().get(j));
                    }
                }
                catch(Exception e)
                {
                    System.out.println("Parent1 creation failed");
                }
                */
            }
        }
        for(int i = 0; i < rockets.size(); i++)
        {
            if(rockets.get(i).getFitness() == rankedFitness.get(1))
            {
                parentIndex2 = i;
                for(int j = 0; j < rockets.get(i).genesCopy.size(); j++)
                {
                    parent2Genes.add(rockets.get(i).genesCopy.get(j));
                }
                i = rockets.size();
                //break;
                /*
                try
                {
                    parent2 = new LunarModule(rockets.get(i).x, rockets, rockets.get(i).num);
                    for(int j = 0; j < rockets.get(i).getGenes().size(); j++)
                    {
                        parent2.getGenes().add(rockets.get(i).getGenes().get(j));
                    }
                }
                catch(Exception e)
                {
                    System.out.println("Parent2 creation failed");
                }
                */
            }
        }

        for(int i = 0; i < rockets.size(); i++)
        {
            if(rockets.get(i).getFitness() == rankedFitness.get(2))
            {
                clone3Index = i;
                i = rockets.size();
            }
        }

        for(int i = 0; i < rockets.size(); i++)
        {
            if(rockets.get(i).getFitness() == rankedFitness.get(3))
            {
                clone4Index = i;
                i = rockets.size();
            }
        }


        /*
        if(parent1Genes.size() == parent2Genes.size())
        {
            System.out.println("Parent genes size equal");
        }
        else
        {
            System.out.println("Parent genes size not equal");
            System.out.println(parent1Genes.size() + ", " + parent2Genes.size());
        }

        for(int i = 0; i < rockets.size(); i++)
        {
            rockets.get(i).genes.clear();
        }
        */

        /*
        System.out.print("Parent 1 Genes: ");
        for(int i = 0; i < rockets.get(parentIndex1).genesCopy.size(); i++)
        {
            System.out.print(Math.round(rockets.get(parentIndex1).genesCopy.get(i)) + ", ");
        }
        System.out.println();
        System.out.print("Parent 2 Genes: ");
        for(int i = 0; i < rockets.get(parentIndex2).genesCopy.size(); i++)
        {
            System.out.print(Math.round(rockets.get(parentIndex2).genesCopy.get(i)) + ", ");
        }
        System.out.println();
        */

        /*
        for(int i = 0; i < parent1Genes.size(); i++)
        {
            rockets.get(parentIndex1).genes.add(parent1Genes.get(i));
        }
        for(int i = 0; i < parent2Genes.size(); i++)
        {
            rockets.get(parentIndex2).genes.add(parent2Genes.get(i));
        }
        */
        clone1Index = parentIndex1;
        clone2Index = parentIndex2;


        for(int i = 0; i < rockets.size(); i++)
        {
            rockets.get(i).genes.clear();
        }


        int j = 0;
        //Crossover
        for(int i = 0; i < rockets.size(); i++)
        {

            if((i != parentIndex1) && (i != parentIndex2) && (i != clone3Index) && (i != clone4Index))
            {
                //rockets.get(i).genes.addAll(parent1Genes);
                for(int l = 0; l < parent1Genes.size(); l++)
                {
                    int rand = (int)(Math.random()*2);
                    if(rand == 0)
                    {
                        rockets.get(i).genes.add(parent1Genes.get(l));
                    }
                    else
                    {
                        rockets.get(i).genes.add(parent2Genes.get(l));
                    }
                }
            }
            else
            {
                if(i == parentIndex1)
                {
                    for(int c1 = 0; c1 < parent1Genes.size(); c1++)
                    {
                        rockets.get(i).genes.add(parent1Genes.get(c1));
                    }
                }
                else if(i == parentIndex2)
                {
                    for(int c1 = 0; c1 < parent2Genes.size(); c1++)
                    {
                        rockets.get(i).genes.add(parent2Genes.get(c1));
                    }
                }
            }
        }
        /*
        System.out.print("Clone 1 Genes: ");
        for(int i = 0; i < rockets.get(parentIndex1).genes.size(); i++)
        {
            System.out.print(Math.round(rockets.get(parentIndex1).genes.get(i)) + ", ");
        }
        System.out.println();
        System.out.print("Clone 2 Genes: ");
        for(int i = 0; i < rockets.get(parentIndex2).genes.size(); i++)
        {
            System.out.print(Math.round(rockets.get(parentIndex2).genes.get(i)) + ", ");
        }
        System.out.println();
        */


        //Mutation
        for(int i = 0; i < rockets.size(); i++)
        {
            if((i != parentIndex1) && (i != parentIndex2) && (i != clone3Index) && (i != clone4Index))
            {
                for(int m = 0; m < rockets.get(i).genes.size(); m++)
                {
                    int rand = (int)(Math.random()*10)+1;
                    if(rand <= 2)
                    {
                        if(m > rockets.get(i).genes.size()/2)
                        {
                            if(m%2 == 0)
                            {
                                double mutatedGene = rockets.get(i).getGenes().get(m)*0.9;
                                rockets.get(i).getGenes().set(m, mutatedGene);
                            }
                            else
                            {
                                double mutatedGene = rockets.get(i).getGenes().get(m)*0.8;
                                rockets.get(i).getGenes().set(m, mutatedGene);
                            }
                        }
                        else
                        {
                            if(m%2 == 0)
                            {
                                double mutatedGene = rockets.get(i).getGenes().get(m)*0.8;
                                rockets.get(i).getGenes().set(m, mutatedGene);
                            }
                            else
                            {
                                double mutatedGene = rockets.get(i).getGenes().get(m)*1.1;
                                rockets.get(i).getGenes().set(m, mutatedGene);
                            }
                        }

                    }
                }
            }
        }

        int generationRandom = (int)(Math.random()*11)+20;
        if(generation >= generationRandom)
        {
            int rand = (int)(Math.random()*15);
            rockets.get(rand).genes.set(0,mutation1);
            rockets.get(rand).genes.set(1,mutation2);
            rockets.get(rand).genes.set(2,mutation3);
        }

        /*
        for(int i = 0; i < rockets.size(); i++)
        {
            System.out.print(rockets.get(i).num + ": ");
            for(int c = 0; c < rockets.get(i).genes.size(); c++)
            {
                System.out.print(Math.round(rockets.get(i).genes.get(c)) + ", ");
            }
            System.out.println();
            System.out.println();
        }
        */


        for(int i = 0; i < rockets.size(); i++)
        {
            rockets.get(i).genesCopy.clear();
        }
        for(int i = 0; i < rockets.size(); i++)
        {
            for(int k = 0; k < rockets.get(i).genes.size(); k++)
            {
                rockets.get(i).genesCopy.add(rockets.get(i).genes.get(k));
            }
        }


        for(int i = 0; i < rockets.size(); i++)
        {

            /*
            System.out.print("copy: ");
            for(int d = 0; d < rockets.get(i).genesCopy.size(); d++)
            {
                System.out.print(Math.round(rockets.get(i).genesCopy.get(d)) + ", ");
            }
            System.out.println();
            System.out.print("original: ");
            for(int d = 0; d < rockets.get(i).genes.size(); d++)
            {
                System.out.print(Math.round(rockets.get(i).genes.get(d)) + ", ");
            }
            System.out.println();
            System.out.println();
            */

            /*
            for(int a = 0; a < rockets.get(i).genes.size(); a++)
            {
                System.out.print(Math.round(rockets.get(i).genes.get(a)) + ", ");
            }
            */
            //System.out.println();
            //System.out.println(rockets.get(i).genes.size() + ", copy: " + rockets.get(i).genesCopy.size());
            /*
            if(rockets.get(i).getFitness() <= 0.3)
            {
                System.out.println("DONE!");
            }
            */
        }
    }



    /*
    public void update()
    {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.filledRectangle(x,pos, 2, 8);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(0.02);
        StdDraw.line( 0,5 ,100 ,5 );
        StdDraw.setPenRadius();
        StdDraw.text(x,pos-10,"Fuel: " + getFuel());
        StdDraw.text(x,pos-15,"Altitude: " + (int)(getPos()));
        StdDraw.picture(x,getPos(),"LunarModule.png");

        if(getFuel() <= 100 && getFuel() != 0)
        {
            StdDraw.setPenColor(Color.RED);
            //StdDraw.text(11, 85, "Low Fuel!");
        }
        else if(getFuel() == 0)
        {
            StdDraw.setPenColor(Color.RED);
            //StdDraw.text(11, 85, "Out of Fuel!");
        }

        StdDraw.setPenColor(Color.BLACK);
        if(StdDraw.isKeyPressed(32) && getFuel() != 0)
        {
            if(pos <= 10)
            {
                setState(0);
                if(Math.abs(getVelocity()) >= 0.1)
                {
                    StdDraw.picture(x,10,"Explosion.png");
                    setFuel(200);
                }
                else
                {
                    //StdDraw.text(x,50, "Landed successfully!");
                    setFuel(200);
                }
                StdDraw.show(1000);
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.filledRectangle(x, pos, 2, 8);
                setVelocity(0);
                setPos(50);
            }
            StdDraw.picture(x,getPos()-10,"Flame.png");
            setState(1); //Upwards
            setVelocity(getVelocity()+0.007-0.0035);
            setPos(getPos()+getVelocity());
            setFuel(getFuel()-1);
            StdDraw.pause(10);
        }
        else
        {
            if(getState() == 1 || getState() == 2)
            {
                if(getPos() > 10)
                {
                    setState(2); //Falling
                    setVelocity(getVelocity()-0.0035);
                    setPos(getPos()+getVelocity());
                    StdDraw.pause(10);
                }
                else
                {
                    setState(0);
                    if(Math.abs(getVelocity()) >= 0.1)
                    {
                        StdDraw.picture(x,10,"Explosion.png");
                        setFuel(200);
                    }
                    else
                    {
                        //StdDraw.text(x,50, "Landed successfully!");
                        setFuel(200);
                    }
                    StdDraw.show(1000);
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.filledRectangle(x, pos, 2, 8);
                    setVelocity(0);
                    setPos(50);
                }
            }
        }
        StdDraw.show();
    }
    */
}
