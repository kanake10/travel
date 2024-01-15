package com.example.travel.repo

import com.example.travel.core.Resource
import com.example.travel.models.Result

interface PropertyRepository {
    suspend fun getListings(): Resource<ArrayList<Result>>
}