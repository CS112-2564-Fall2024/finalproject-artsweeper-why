package edu.miracosta.cs112.finalproject.finalproject;

import java.util.ArrayList;

public class Player {
    private static ArrayList<Tile> findNeighbors(Tile[][] map, int row, int col){
        ArrayList<Tile> tiles = new ArrayList<>();

        if(row+1<map.length){}
        Tile up = map[row+1][col];
        Tile down = map[row-1][col];
        Tile left = map[row][col-1];
        Tile right = map[row][col+1];

        if(!up.getIsMine() && !up.getIsClicked()){
            tiles.add(up);
            up.setClicked(true);
            tiles.addAll(findNeighbors(map, up.getY(), up.getX()));
        }
        if(!down.getIsMine() && !down.getIsClicked()){
            tiles.add(down);
            down.setClicked(true);
            tiles.addAll(findNeighbors(map, down.getY(), down.getX()));
        }
        if(!left.getIsMine() && !left.getIsClicked()){
            tiles.add(left);
            left.setClicked(true);
            tiles.addAll(findNeighbors(map, left.getY(), left.getX()));
        }
        if(!right.getIsMine() && !right.getIsClicked()){
            tiles.add(right);
            right.setClicked(true);
            tiles.addAll(findNeighbors(map, right.getY(), right.getX()));
        }
        /*
        for(int i = row-1; i <= row+1; i++){
            for(int j = col-1; j <= col+1; j++){
                if((i != row || j != col) && (i >= 0 && i < map.length && j >= 0 && j < map[0].length)){

                    Tile tile = map[i][j];
                    if(!tile.getIsMine() && !tile.getIsClicked()){
                        tile.setClicked(true);
                        tiles.add(tile);
                        tiles.addAll(findNeighbors(map, i, j));
                    }

                }
            }
        }*/

        return tiles;
    }

    static void clickTile(Tile[][] map, Tile t) {
        System.out.println("Clicked tile at " + t.getX() + ", " + t.getY());
        if(t.getIsMine()){
            System.out.println("Mine");
        }
        else{
            System.out.println("Safe");
            ArrayList<Tile> neighbors = findNeighbors(map, t.getX(), t.getY());
            System.out.println("Neighbor Amount: " + neighbors.size());
            for(Tile neighbor : neighbors){
                neighbor.getLabel().setStyle("-fx-background-color: rgb(255,0,0);");
            }
        }
    }
}