package org.example.Entity;

/**
 * 电影类型实体类(暂时无用）
 */

public class MovieType {




    private String name;

    private Integer value;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MovieType{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

}
