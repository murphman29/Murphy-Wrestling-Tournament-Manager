/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataClasses;

import java.util.ArrayList;
import java.util.Collections;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import wrestlingtournamentcli.Model;

/**
 *
 * @author slaya
 */
public class WrestlerTest {

    Wrestler lightWeight;
    Wrestler heavyWeight;
    Wrestler seeded;
    Wrestler nonseeded;
    Wrestler superiorRating;
    Wrestler inferiorRating;

    public WrestlerTest() {
        try {
            lightWeight = new Wrestler("Light", "Weight", 12, 105, 10, 100);
            heavyWeight = new Wrestler("Heavy", "Weight", 12, 220, 5, 100);
            seeded = new Wrestler("Seeded", "Wrestler", 12, 132, 10, 100);
            seeded.setSeed(1);
            nonseeded = new Wrestler("NonSeeded", "Wrestler", 12, 132, 10, 100);
            superiorRating = new Wrestler("Superior", "Rating", 12, 132, 100, 100);
            inferiorRating = new Wrestler("Inferior", "Rating", 12, 132, 7, 100);
        } catch (Exception e) {
            System.out.println("Couldn't initialize test wrestlers\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        Model m = new Model();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of compareTo method, of class Wrestler.
     */
    @Test
    public void testCompareToAll() {
        System.out.println("compareTo -- All-Encompassing");

        ArrayList<Wrestler> control = new ArrayList();
        control.add(lightWeight);
        control.add(seeded);
        control.add(superiorRating);
        control.add(nonseeded);
        control.add(inferiorRating);
        control.add(heavyWeight);
        ArrayList<Wrestler> test = new ArrayList();
        test.add(lightWeight);
        test.add(seeded);
        test.add(heavyWeight);
        test.add(superiorRating);
        test.add(inferiorRating);
        test.add(nonseeded);
        Collections.sort(test);
        for (Wrestler w : test) {
            System.out.println(w);
        }

        assertEquals(control, test);
    }

    @Test
    public void testCompareToWeightClass() {
        System.out.println("compareTo -- Wrestler's WeightClass");

        ArrayList<Wrestler> control = new ArrayList();
        control.add(lightWeight);
        control.add(heavyWeight);
        ArrayList<Wrestler> test = new ArrayList();
        test.add(heavyWeight);
        test.add(lightWeight);
        Collections.sort(test);

        assertEquals(control, test);
    }

    @Test
    public void testCompareToSeed() {
        System.out.println("compareTo -- Wrestler's Seed");

        ArrayList<Wrestler> control = new ArrayList();
        control.add(seeded);
        control.add(nonseeded);
        ArrayList<Wrestler> test = new ArrayList();
        test.add(nonseeded);
        test.add(seeded);
        Collections.sort(test);

        assertEquals(control, test);
    }

    @Test
    public void testCompareToRating() {
        System.out.println("compareTo -- Wrestler's Seed");

        ArrayList<Wrestler> control = new ArrayList();
        control.add(superiorRating);
        control.add(inferiorRating);
        ArrayList<Wrestler> test = new ArrayList();
        test.add(inferiorRating);
        test.add(superiorRating);
        Collections.sort(test);

        assertEquals(control, test);
    }
}
