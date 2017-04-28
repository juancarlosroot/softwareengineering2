package graph;

import java.io.*;
import java.util.Scanner;
import java.util.*;

public class Graph {

    int nNodes, init, end;
    String inputFile;
    ArrayList<ArrayList<String>> defs;
    ArrayList<ArrayList<String>> uses;
    ArrayList<ArrayList<Integer>> list;
    ArrayList<ArrayList<Integer>> primePaths;
    ArrayList<ArrayList<Integer>> simplePaths;
    ArrayList<ArrayList<Integer>> epc;
    ArrayList<ArrayList<Integer>> primeAux;
    Set<String> vars;
    ArrayList<String> varList;

    public Graph(String input) {

        try {
            Scanner scanner
                    = new Scanner(
                            new BufferedReader(
                                    new FileReader(input)
                            )
                    );
            nNodes = Integer.parseInt(scanner.nextLine());
            init = Integer.parseInt(scanner.nextLine());
            end = Integer.parseInt(scanner.nextLine());

            simplePaths = new ArrayList<ArrayList<Integer>>();
            vars = new HashSet<String>();

            list = new ArrayList<ArrayList<Integer>>();

            for (int i = 0; i < nNodes; i++) {
                ArrayList<Integer> actual = new ArrayList<Integer>();
                String[] line = scanner.nextLine().split(" ");
                for (String element : line) {
                    actual.add(Integer.parseInt(element));

                }
                list.add(actual);
            }

            defs = new ArrayList<ArrayList<String>>();

            for (int i = 0; i < nNodes; i++) {
                ArrayList<String> actual = new ArrayList<String>();
                String[] line = scanner.nextLine().split(" ");
                for (String element : line) {
                    actual.add(element);
                }
                defs.add(actual);
            }

            uses = new ArrayList<ArrayList<String>>();

            for (int i = 0; i < nNodes; i++) {
                ArrayList<String> actual = new ArrayList<String>();
                String[] line = scanner.nextLine().split(" ");
                for (String element : line) {
                    actual.add(element);
                }
                uses.add(actual);
            }

            scanner.close();

            for (ArrayList<String> element : defs) {
                for (String varElement : element) {
                    vars.add(varElement);
                }
            }
            for (ArrayList<String> element : uses) {
                for (String varElement : element) {
                    vars.add(varElement);
                }
            }

            varList = new ArrayList<String>(vars);

        } catch (FileNotFoundException fnfe) {
            fnfe.getStackTrace();
        }

    }

    public void growPaths(ArrayList<ArrayList<Integer>> paths) {
        ArrayList<ArrayList<Integer>> newPaths = new ArrayList<ArrayList<Integer>>();
        if (paths.get(0).size() == 3) {
            System.out.println("size 3 paths");
            for (ArrayList<Integer> path : paths) {
                epc.add(path);
            }
        }

        for (ArrayList<Integer> path : paths) {
            System.out.print(path);
            if ((path.get(0) == path.get(path.size() - 1)) && path.size() > 1) {
                System.out.println("*");
                primePaths.add(path);
            } else {
                ArrayList<Integer> neighbors = list.get(path.get(path.size() - 1));
                //System.out.println("neighbors("+path.get(path.size()-1)+") = "+neighbors);
                neighbors.removeAll(path.subList(1, path.size()));
                //System.out.println("Removed: "+neighbors);
                if (neighbors.size() == 0) {
                    System.out.println("!");
                    primePaths.add(path);
                } else {
                    System.out.println("");
                    for (int i : neighbors) {
                        if (!path.contains(i) || i == path.get(0)) {
                            newPaths.add(new ArrayList<Integer>(path));
                            newPaths.get(newPaths.size() - 1).add(i);
                            simplePaths.add(newPaths.get(newPaths.size() - 1));
                        }
                    }
                }
            }
        }
        System.out.println("-----------------------");
        if (newPaths.size() > 0) {
            growPaths(newPaths);
        }
    }

    public void findPrimePaths(int init, int end, ArrayList<ArrayList<Integer>> list) {
        ArrayList<ArrayList<Integer>> paths = new ArrayList<ArrayList<Integer>>();
        epc = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < list.size(); i++) {
            ArrayList<Integer> actual = new ArrayList<Integer>();
            actual.add(i);
            paths.add(actual);
        }
        primePaths = new ArrayList<ArrayList<Integer>>();
        growPaths(paths);
        primeAux = new ArrayList<ArrayList<Integer>>(primePaths);
        for (ArrayList<Integer> p : primePaths) {
            int pIndex = primePaths.indexOf(p);
            if (pIndex > 0) {
                ArrayList<ArrayList<Integer>> minorLists = new ArrayList<ArrayList<Integer>>(primePaths.subList(0, pIndex));
                for (ArrayList<Integer> actual : minorLists) {
                    if (Collections.indexOfSubList(p, actual) != -1) {
                        primeAux.remove(actual);
                    }
                    //	primePaths.remove(actual);
                }
            }
        }
    }

    public void duPaths(String var) {

        System.out.println("\t\tdu( " + var + " )");
        for (ArrayList<Integer> sPath : simplePaths) {

            if (defs.get(sPath.get(0)).contains(var)) {
                for (int i = 1; i <= sPath.size(); i++) {

                    if ((i < sPath.size() - 1) && defs.get(sPath.get(i)).contains(var)) {
                        break;
                    }

                    if ((i == sPath.size() - 1) && uses.get(sPath.get(i)).contains(var)) {
                        ArrayList<Integer> base1List = new ArrayList<Integer>();
                        for (Integer element : sPath) {
                            base1List.add(element + 1);
                        }
                        System.out.println(base1List);
                    }

                }
            }

        }

    }

    public void printData() {
        //
        System.out.println("Prime paths:");
        for (ArrayList<Integer> p : primeAux) {
            System.out.println(p.toString());
        }
        System.out.println(primeAux.toString());
        System.out.println("size of primePaths: " + primeAux.size());
        //
        System.out.println("EPC: ");
        for (ArrayList<Integer> p : epc) {
            System.out.println(p);
        }
        System.out.println("size of EPC: " + epc.size());
        //
        System.out.println("Defs: ");
        for (ArrayList<String> def : defs) {
            System.out.println(def);
        }
        //
        System.out.println("Uses: ");
        for (ArrayList<String> use : uses) {
            System.out.println(use);
        }
    }

    public void runner() {

        System.out.println("nNodes: \t" + nNodes);
        System.out.println("init: \t\t" + init);
        System.out.println("end: \t\t" + end);
        int node = 0;
        for (ArrayList<Integer> al : list) {
            System.out.println(node + "\t" + al);
            node++;
        }

        findPrimePaths(init, end, list);
        for (String varElement : varList) {
            duPaths(varElement);
        }
    }

    public int getnNodes() {
        return nNodes;
    }

    public void setnNodes(int nNodes) {
        this.nNodes = nNodes;
    }

    public int getInit() {
        return init;
    }

    public void setInit(int init) {
        this.init = init;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public ArrayList<ArrayList<String>> getDefs() {
        return defs;
    }

    public void setDefs(ArrayList<ArrayList<String>> defs) {
        this.defs = defs;
    }

    public ArrayList<ArrayList<String>> getUses() {
        return uses;
    }

    public void setUses(ArrayList<ArrayList<String>> uses) {
        this.uses = uses;
    }

    public ArrayList<ArrayList<Integer>> getPrimePaths() {
        return primePaths;
    }

    public void setPrimePaths(ArrayList<ArrayList<Integer>> primePaths) {
        this.primePaths = primePaths;
    }

    public ArrayList<ArrayList<Integer>> getSimplePaths() {
        return simplePaths;
    }

    public void setSimplePaths(ArrayList<ArrayList<Integer>> simplePaths) {
        this.simplePaths = simplePaths;
    }

    public ArrayList<ArrayList<Integer>> getEpc() {
        return epc;
    }

    public void setEpc(ArrayList<ArrayList<Integer>> epc) {
        this.epc = epc;
    }

    public ArrayList<ArrayList<Integer>> getPrimeAux() {
        return primeAux;
    }

    public void setPrimeAux(ArrayList<ArrayList<Integer>> primeAux) {
        this.primeAux = primeAux;
    }
   
//    public static void main(String[] args) {
//        Graph newGraph
//                = new Graph(
//                        System.getProperty("user.dir")
//                        + "/" + "Salidas" + "/" + "prueba1_main.txt");
//        newGraph.runner();
//        newGraph.printData();
//    }

}
