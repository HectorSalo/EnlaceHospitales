package com.skysam.enlacehospitales.ui.main

import com.skysam.enlacehospitales.dataClasses.Member

/**
 * Created by Hector Chirinos on 13/08/2023.
 */

interface OnClickGuard {
 fun view(member: Member)
}