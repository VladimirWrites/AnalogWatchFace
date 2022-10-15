package com.vlad1m1r.watchface.components.ticks

import com.vlad1m1r.watchface.components.ticks.usecase.AdjustToChin
import com.vlad1m1r.watchface.model.Point
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class AdjustToChinShould(private val tickRotation: Double, private val expectedResult: Double) {

    private val adjustToChin = AdjustToChin()

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(0.0, 1.0),
            arrayOf(0.07853981633974483, 1.0),
            arrayOf(0.15707963267948966, 1.0),
            arrayOf(0.23561944901923448, 1.0),
            arrayOf(0.3141592653589793, 1.0),
            arrayOf(0.39269908169872414, 1.0),
            arrayOf(0.47123889803846897, 1.0),
            arrayOf(0.5497787143782138, 1.0),
            arrayOf(0.6283185307179586, 1.0),
            arrayOf(0.7068583470577035, 1.0),
            arrayOf(0.7853981633974483, 1.0),
            arrayOf(0.863937979737193, 1.0),
            arrayOf(0.9424777960769379, 1.0),
            arrayOf(1.0210176124166828, 1.0),
            arrayOf(1.0995574287564276, 1.0),
            arrayOf(1.1780972450961724, 1.0),
            arrayOf(1.2566370614359172, 1.0),
            arrayOf(1.335176877775662, 1.0),
            arrayOf(1.413716694115407, 1.0),
            arrayOf(1.4922565104551517, 1.0),
            arrayOf(1.5707963267948966, 1.0),
            arrayOf(1.6493361431346414, 1.0),
            arrayOf(1.727875959474386, 1.0),
            arrayOf(1.806415775814131, 1.0),
            arrayOf(1.8849555921538759, 1.0),
            arrayOf(1.9634954084936207, 1.0),
            arrayOf(2.0420352248333655, 1.0),
            arrayOf(2.1205750411731104, 1.0),
            arrayOf(2.199114857512855, 1.0),
            arrayOf(2.2776546738526, 1.0),
            arrayOf(2.356194490192345, 1.0),
            arrayOf(2.4347343065320897, 1.0),
            arrayOf(2.5132741228718345, 1.0),
            arrayOf(2.5918139392115793, 1.0),
            arrayOf(2.670353755551324, 1.0),
            arrayOf(2.748893571891069, 0.9741529802631546),
            arrayOf(2.827433388230814, 0.9463160018144404),
            arrayOf(2.9059732045705586, 0.9255736742986876),
            arrayOf(2.9845130209103035, 0.9112186132092026),
            arrayOf(3.0630528372500483, 0.9027829786345429),
            arrayOf(3.141592653589793, 0.9),
            arrayOf(3.2201324699295375, 0.9027829786345429),
            arrayOf(3.2986722862692828, 0.9112186132092026),
            arrayOf(3.3772121026090276, 0.9255736742986876),
            arrayOf(3.455751918948772, 0.9463160018144404),
            arrayOf(3.5342917352885173, 0.9741529802631546),
            arrayOf(3.612831551628262, 1.0),
            arrayOf(3.6913713679680074, 1.0),
            arrayOf(3.7699111843077517, 1.0),
            arrayOf(3.848451000647496, 1.0),
            arrayOf(3.9269908169872414, 1.0),
            arrayOf(4.005530633326986, 1.0),
            arrayOf(4.084070449666731, 1.0),
            arrayOf(4.162610266006476, 1.0),
            arrayOf(4.241150082346221, 1.0),
            arrayOf(4.319689898685966, 1.0),
            arrayOf(4.39822971502571, 1.0),
            arrayOf(4.476769531365455, 1.0),
            arrayOf(4.5553093477052, 1.0),
            arrayOf(4.633849164044944, 1.0),
            arrayOf(4.71238898038469, 1.0),
            arrayOf(4.790928796724435, 1.0),
            arrayOf(4.869468613064179, 1.0),
            arrayOf(4.948008429403925, 1.0),
            arrayOf(5.026548245743669, 1.0),
            arrayOf(5.105088062083413, 1.0),
            arrayOf(5.183627878423159, 1.0),
            arrayOf(5.262167694762903, 1.0),
            arrayOf(5.340707511102648, 1.0),
            arrayOf(5.419247327442394, 1.0),
            arrayOf(5.497787143782138, 1.0),
            arrayOf(5.576326960121883, 1.0),
            arrayOf(5.654866776461628, 1.0),
            arrayOf(5.733406592801372, 1.0),
            arrayOf(5.811946409141117, 1.0),
            arrayOf(5.890486225480862, 1.0),
            arrayOf(5.969026041820607, 1.0),
            arrayOf(6.047565858160352, 1.0),
            arrayOf(6.126105674500097, 1.0),
        )
    }

    @Test
    fun calculateValueToTransformCircleToSquare() {
        assertEquals(adjustToChin(tickRotation, Point(100f, 100f), 10), expectedResult, 0.000001)
    }
}