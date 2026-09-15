package com.example.sih26087.data.repository

import com.example.sih26087.data.local.MainDao
import com.example.sih26087.data.local.ProgrammeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgrammeRepository @Inject constructor(
    private val mainDao: MainDao
) {
    fun getProgrammes(): Flow<List<ProgrammeEntity>> = mainDao.getProgrammes()

    suspend fun refreshProgrammes() {
        val mockProgrammes = listOf(
            ProgrammeEntity("p1", "Advanced Cooperative Management", "National Institute of Coop", "3 Months", "Any Graduate", 40, "Management"),
            ProgrammeEntity("p2", "Digital Agri-Marketing", "Regional Training Centre", "1 Month", "12th Pass", 60, "Marketing"),
            ProgrammeEntity("p3", "Micro-Finance Operations", "State Coop Union", "6 Weeks", "B.Com/BBA", 30, "Finance"),
            ProgrammeEntity("p4", "Supply Chain ERP Integration", "National Coop Federation", "2 Months", "Any Graduate", 25, "Operations")
        )
        mainDao.insertProgrammes(mockProgrammes)
    }
}
