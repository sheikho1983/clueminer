package org.clueminer.evolution.mo;

import org.clueminer.evolution.singlem.SingleMuteIndividual;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.Solution;

/**
 *
 * @author Tomas Barton
 */
public class MoSolution implements IntegerSolution {

    private final SingleMuteIndividual individual;
    private final MoProblem problem;
    private double[] objectives;
    private int[] solution;
    private double constraintViolationDegree = 0;
    private int numViolatedDegrees = 0;

    public MoSolution(MoProblem problem, SingleMuteIndividual individual) {
        this.individual = individual;
        this.problem = problem;
        this.objectives = new double[problem.evolution.getNumObjectives()];
        this.solution = new int[problem.getNumVars()];
        for (int i = 0; i < solution.length; i++) {

        }
    }

    public void evaluate() {
        for (int i = 0; i < objectives.length; i++) {
            objectives[i] = individual.countFitness(problem.evolution.getObjective(i));
        }
    }

    @Override
    public void setObjective(int index, double value) {
        objectives[index] = value;
    }

    @Override
    public double getObjective(int index) {
        return objectives[index];
    }

    @Override
    public Integer getVariableValue(int index) {
        return solution[index];
        //return individual.getGen(problem.getVar(index));
    }

    @Override
    public void setVariableValue(int index, Integer value) {
        individual.setAlgorithm(null);
    }

    @Override
    public String getVariableValueString(int index) {
        return individual.getGen(problem.getVar(index));
    }

    @Override
    public int getNumberOfVariables() {
        return problem.getNumVars();
    }

    @Override
    public int getNumberOfObjectives() {
        return objectives.length;
    }

    @Override
    public double getOverallConstraintViolationDegree() {
        return constraintViolationDegree;
    }

    @Override
    public void setOverallConstraintViolationDegree(double violationDegree) {
        this.constraintViolationDegree = violationDegree;
    }

    @Override
    public int getNumberOfViolatedConstraints() {
        return numViolatedDegrees;
    }

    @Override
    public void setNumberOfViolatedConstraints(int numberOfViolatedConstraints) {
        this.numViolatedDegrees = numberOfViolatedConstraints;
    }

    @Override
    public Solution copy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setAttribute(Object id, Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getAttribute(Object id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getLowerBound(int index) {
        return problem.getLowerBound(index);
    }

    @Override
    public Integer getUpperBound(int index) {
        return problem.getUpperBound(index);
    }

}