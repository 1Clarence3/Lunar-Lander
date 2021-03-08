# Lunar-Lander
Using Java, I created 15 rockets with a an arraylist of "genes". Each element of this arraylist
represented a single "gene" that coded for whether the rocket would boost or not at that time. From
this, the altitude, fuel, velocity, and acceleration could be tracked for each individual rocket.The
rockets that performed the best (had the lowest impact velocity), acted as the "parent" rockets for the
next generation. The first half of the elements in one of the parent's arraylist as well as the second half of the elements in the other parent's arraylist were copied into the next generation's arraylists. However, a handful of rockets had their sequences directly copied from the previous generation to simulate genetic inheritance. Furthermore, to introduce variation in the sequences, mutations in subequent genes are introduced by adding or subtracting small values (noise) to a randomized element.
