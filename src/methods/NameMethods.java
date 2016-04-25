/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;

/**
 *
 * @author ilyab
 */
public enum NameMethods {
        MethodOfSteepestDescent(){
            private final String name = "Метод наискорейшего спуска"; 
            @Override
            public String toString(){return name;}
        },  
        MethodPitchwise(){
            private final String name = "Метод с постоянным шагом"; 
            @Override
            public String toString(){return name;}
        },
        MethodStepCrushing(){
            private final String name = "Метод с дроблением шага"; 
            @Override
            public String toString(){return name;}
        },
        MethodToDecreaseTheLengthOfThePitch{
            private final String name = "Метод с убыванием длины шага как 1/k"; 
            @Override
            public String toString(){return name;}
        },
        MethodOfRavines{
            private final String name = "Метод оврагов"; 
            @Override
            public String toString(){return name;}
        },
        MethodNewtons{
            private final String name = "Метод Ньютона"; 
            @Override
            public String toString(){return name;}
        },
        MethodDFP{
            private final String name = "Метод Давидона-Флетчера-Пауэла"; 
            @Override
            public String toString(){return name;}
        },
        MethodBFS(){
            private final String name = "Метод Бройдена-Флетчера-Шанно"; 
            @Override
            public String toString(){return name;}
        }
    }
