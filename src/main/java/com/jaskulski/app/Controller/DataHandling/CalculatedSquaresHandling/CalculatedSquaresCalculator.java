package com.jaskulski.app.Controller.DataHandling.CalculatedSquaresHandling;

import com.jaskulski.app.Data.CalculatedSquares;
import com.jaskulski.app.Data.SquareGrid;
import com.jaskulski.app.Data.StartingConditions;

public class CalculatedSquaresCalculator {
    private SquareGrid squareGrid;
    private StartingConditions startingConditions;
    private CalculatedSquares calculatedSquares;
    private int cSSizeX;
    private int cSSizeY;
    private double squareSize;

    public int getCSSizeX(){
        return cSSizeX;
    }

    public int getCSSizeY(){
        return cSSizeY;
    }

    public CalculatedSquaresCalculator(StartingConditions sC1, SquareGrid sG1, CalculatedSquares cS1) {
        this.startingConditions = sC1;
        this.squareGrid = sG1;
        this.calculatedSquares = cS1;
        this.cSSizeX = sG1.squareCorners.length - 1;
        this.cSSizeY = sG1.squareCorners[0].length - 1;
        this.squareSize = startingConditions.getSquareSide();
    }

    public void calculateCS(){
        setCSSizes();
        setAllSquares();
        checkAndSetZeroPoints();
    }

    private void setCSSizes() {
        calculatedSquares.squares = new CalculatedSquares.SingleSquare[cSSizeX][cSSizeY];
    }

    private void setAllSquares(){
        double LeftTop, RightTop, LeftDown, RightDown;
        double LeftTopT, RightTopT, LeftDownT, RightDownT;

        int index = 1;
        for (int j = 0; j < cSSizeY; j++) {
            for (int i = 0; i < cSSizeX; i++) {
                LeftTop = squareGrid.squareCorners[i][j].getProjectOrdinate();
                RightTop = squareGrid.squareCorners[i][j + 1].getProjectOrdinate();
                LeftDown = squareGrid.squareCorners[i + 1][j].getProjectOrdinate();
                RightDown = squareGrid.squareCorners[i + 1][j + 1].getProjectOrdinate();

                LeftTopT = squareGrid.squareCorners[i][j].getTerrainOrdinate();
                RightTopT = squareGrid.squareCorners[i][j + 1].getTerrainOrdinate();
                LeftDownT = squareGrid.squareCorners[i + 1][j].getTerrainOrdinate();
                RightDownT = squareGrid.squareCorners[i + 1][j + 1].getTerrainOrdinate();

                calculatedSquares.squares[i][j] = calculatedSquares.new SingleSquare();
                calculatedSquares.squares[i][j].setIndex(index++);
                calculatedSquares.squares[i][j].setFourCornersXAndY(i, j, squareSize);
                calculatedSquares.squares[i][j].setFourCornersH(LeftTop, RightTop, LeftDown, RightDown);
                calculatedSquares.squares[i][j].setFourTerrainH(LeftTopT, RightTopT, LeftDownT, RightDownT);

            }
        }
    }

    private void checkAndSetZeroPoints() {
        for (SquareGrid.ZeroPoint point : squareGrid.listOfZeroPoints) {
            double notSimplifiedX = point.getOrdinateX();
            double notSimplifiedY = point.getOrdinateY();

            int simplifiedX = (int) (notSimplifiedX/squareSize);
            int simplifiedY = (int) (notSimplifiedY/squareSize);

            double newX = notSimplifiedX;
            double newY = notSimplifiedY;
            double newH = point.getOrdinateH();

            if ((notSimplifiedX % squareSize) == 0){
                if(simplifiedX == 0){
                    calculatedSquares.squares[simplifiedX][simplifiedY].addNewZeroPoint(newX, newY, newH, 0);
                    System.out.println("Dodano nowy PZ do CS: "+(simplifiedX)+" "+simplifiedY);
                    System.out.format("PZ1: %.2f %.2f %.2f ", newX, newY, newH);
                    System.out.println("");
                } else {
                    calculatedSquares.squares[simplifiedX - 1][simplifiedY].addNewZeroPoint(newX, newY, newH, 0);
                    System.out.println("Dodano nowy PZ do CS: " + (simplifiedX - 1) + " " + simplifiedY);
                    System.out.format("PZ1: %.2f %.2f %.2f ", newX, newY, newH);
                    System.out.println("");
                    if (simplifiedX < calculatedSquares.squares.length) {
                        calculatedSquares.squares[simplifiedX][simplifiedY].addNewZeroPoint(newX, newY, newH, 0);
                        System.out.println("Dodano nowy PZ do CS: " + (simplifiedX) + " " + simplifiedY);
                        System.out.format("PZ1: %.2f %.2f %.2f ", newX, newY, newH);
                        System.out.println("");
                    }
                }
            } else if ((notSimplifiedY % squareSize) == 0){
                if(simplifiedX == 0){
                    calculatedSquares.squares[simplifiedX][simplifiedY].addNewZeroPoint(newX, newY, newH, 0);
                    System.out.println("Dodano nowy PZ do CS: "+(simplifiedX)+" "+simplifiedY);
                    System.out.format("PZ1: %.2f %.2f %.2f ", newX, newY, newH);
                    System.out.println("");
                } else {
                    calculatedSquares.squares[simplifiedX][simplifiedY - 1].addNewZeroPoint(newX, newY, newH, 0);
                    System.out.println("Dodano nowy PZ do CS: " + (simplifiedX) + " " + (simplifiedY - 1));
                    System.out.format("PZ1: %.2f %.2f %.2f ", newX, newY, newH);
                    System.out.println("");
                    if (simplifiedX < calculatedSquares.squares.length) {
                        calculatedSquares.squares[simplifiedX][simplifiedY].addNewZeroPoint(newX, newY, newH, 0);
                        System.out.println("Dodano nowy PZ do CS: " + (simplifiedX) + " " + simplifiedY);
                        System.out.format("PZ1: %.2f %.2f %.2f ", newX, newY, newH);
                        System.out.println("");
                    }
                }
            }
        }
    }

    public void calculateAllAreasAndVolumes(){
        for (int j = 0; j < cSSizeY; j++) {
            for (int i = 0; i < cSSizeX; i++) {
                calculateAreaAndVolume(calculatedSquares.squares[i][j]);
                System.out.println("Testowa wartość objętości dla "+i+" "+j+" "+calculatedSquares.squares[i][j].getAddVolume()+" -"+calculatedSquares.squares[i][j].getSubtractVolume());
            }
        }
    }

    private void calculateAreaAndVolume(CalculatedSquares.SingleSquare square1) {
        double diffH = calculateAvgHDiff(square1);

        if (square1.zeroSquarePoints.isEmpty()) {
            if(diffH > 0){
                square1.setAddArea(squareSize*squareSize);
                square1.setAddVolume(diffH*squareSize*squareSize);

                square1.setSubtractArea(0);
                square1.setSubtractVolume(0);
            } else if (diffH < 0){
                square1.setAddArea(0);
                square1.setAddVolume(0);

                square1.setSubtractArea(squareSize*squareSize);
                square1.setSubtractVolume(diffH*squareSize*squareSize);
            }
        } else {
            calculateWithZP(square1);
        }
    }

    private void calculateWithZP(CalculatedSquares.SingleSquare square1){
        double step = squareSize/100;
        double relativeZeroX1 = calculateRelativeOrd(square1.zeroSquarePoints.get(0).getOrdinateX());
        double relativeZeroY1 = calculateRelativeOrd(square1.zeroSquarePoints.get(0).getOrdinateY());

        double relativeZeroX2 = calculateRelativeOrd(square1.zeroSquarePoints.get(1).getOrdinateX());
        double relativeZeroY2 = calculateRelativeOrd(square1.zeroSquarePoints.get(1).getOrdinateY());

        double sumAddArea = 0;
        double sumAddVolume = 0;

        double sumSubtractArea = 0;
        double sumSubtractVolume = 0;

        double[] calculatedVector = new double[2];
        double[] lineVector = {relativeZeroX2 - relativeZeroX1, relativeZeroY2 - relativeZeroY1};
        double crossProduct;

        for (int i = 0; i<100; i++){
            for (int j = 0; j<100; j++) {
                calculatedVector[0] = relativeZeroX2 - (i*squareSize);
                calculatedVector[1] = relativeZeroY2 - (j*squareSize);
                crossProduct = lineVector[0]*calculatedVector[1] - lineVector[1]*calculatedVector[0];
                if (crossProduct > 0){
                    sumAddArea += step*step;
                    sumAddVolume += sumAddArea*calculateLocalHDiff(square1, i*squareSize, j*squareSize);
                } else {
                    sumSubtractArea += step*step;
                    sumSubtractVolume += sumSubtractArea*calculateLocalHDiff(square1, i*squareSize, j*squareSize);
                }
            }
        }

        square1.setAddArea(sumAddArea);
        square1.setAddVolume(sumAddVolume);
        square1.setSubtractArea(sumSubtractArea);
        square1.setSubtractVolume(sumSubtractVolume);
    }

    private double calculateRelativeOrd(double ord1){
        int relativeIndex = (int) (ord1/squareSize);
        return (ord1 - (relativeIndex * squareSize));
    }

    private double calculateLocalHDiff(CalculatedSquares.SingleSquare square1, double x1, double y1){
        double partOfX = x1/squareSize;
        double partOfY = y1/squareSize;
        double[] partsOfProjectH = new double[4];
        double[] partsOfTerrainH = new double[4];

        partsOfProjectH[0] = square1.leftTopCor.getOrdinateH()*partOfX*partOfY;
        partsOfTerrainH[0] = square1.leftTopCor.getTerrainH()*partOfX*partOfY;

        partsOfProjectH[1] = square1.rightTopCor.getOrdinateH()*(1-partOfX)*partOfY;
        partsOfTerrainH[1] = square1.rightTopCor.getTerrainH()*(1-partOfX)*partOfY;

        partsOfProjectH[2] = square1.leftDownCor.getOrdinateH()*partOfX*(1-partOfY);
        partsOfTerrainH[2] = square1.leftDownCor.getTerrainH()*partOfX*(1-partOfY);

        partsOfProjectH[3] = square1.rightDownCor.getOrdinateH()*(1-partOfX)*(1-partOfY);
        partsOfTerrainH[3] = square1.rightDownCor.getTerrainH()*(1-partOfX)*(1-partOfY);

        double sumProjctH = 0;
        double sumTerrainH = 0;
        for (int i = 0; i<4; i++){
            sumProjctH += partsOfProjectH[i];
            sumTerrainH += partsOfTerrainH[i];
        }
        return (sumProjctH - sumTerrainH);
    }

    private double calculateAvgHDiff(CalculatedSquares.SingleSquare square1){
        double sumProjectH = 0;
        double sumTerrainH = 0;

        sumProjectH += square1.leftTopCor.getOrdinateH();
        sumProjectH += square1.rightTopCor.getOrdinateH();
        sumProjectH += square1.leftDownCor.getOrdinateH();
        sumProjectH += square1.rightDownCor.getOrdinateH();

        sumTerrainH += square1.leftTopCor.getTerrainH();
        sumTerrainH += square1.rightTopCor.getTerrainH();
        sumTerrainH += square1.leftDownCor.getTerrainH();
        sumTerrainH += square1.rightDownCor.getTerrainH();

        return ((sumProjectH - sumTerrainH)/4);
    }
}
