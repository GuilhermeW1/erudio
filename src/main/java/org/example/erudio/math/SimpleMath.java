package org.example.erudio.math;

import org.springframework.web.bind.annotation.PathVariable;

public class SimpleMath {
    public Double sum(@PathVariable Double num1, @PathVariable Double  num2) {
        return num1 + num2;
    }

    public Double subtract(@PathVariable Double num1, @PathVariable Double  num2) {
        return num1 - num2;
    }

    public Double multiplication(@PathVariable Double num1, @PathVariable Double  num2) {
        return num1 * num2;
    }

    public Double division(@PathVariable Double num1, @PathVariable Double  num2) {
        return(num1) /(num2);
    }

    public Double squareRoot(@PathVariable Double num1) {
        return Math.sqrt(num1);
    }

    public Double mean(@PathVariable Double num1, @PathVariable Double  num2) {
        return (num1 + num2) / 2;
    }
}
