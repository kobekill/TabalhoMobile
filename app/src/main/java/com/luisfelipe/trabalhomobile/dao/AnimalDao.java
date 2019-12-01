package com.luisfelipe.trabalhomobile.dao;

import android.content.Context;

import com.luisfelipe.trabalhomobile.dao.helper.DaoHelper;
import com.luisfelipe.trabalhomobile.model.Animal;

public class AnimalDao extends DaoHelper<Animal> {

    public AnimalDao(Context c) {
        super(c, Animal.class);
    }

}
