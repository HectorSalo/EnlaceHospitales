package com.skysam.enlacehospitales.ui.hlc.emergencys

import com.skysam.enlacehospitales.dataClasses.emergency.Emergency

/**
 * Created by Hector Chirinos on 20/08/2023.
 */

interface OnClick {
 fun view(emergency: Emergency)
 fun finish(emergency: Emergency)
}