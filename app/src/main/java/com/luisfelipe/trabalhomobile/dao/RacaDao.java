package com.luisfelipe.trabalhomobile.dao;

import android.content.Context;

import com.luisfelipe.trabalhomobile.dao.helper.DaoHelper;
import com.luisfelipe.trabalhomobile.model.Raca;

public class RacaDao  extends DaoHelper<Raca> {
    public RacaDao(Context c) {
        super(c, Raca.class);
    }

}
