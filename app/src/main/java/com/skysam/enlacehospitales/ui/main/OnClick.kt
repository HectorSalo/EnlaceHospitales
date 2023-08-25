package com.skysam.enlacehospitales.ui.main

import com.skysam.enlacehospitales.dataClasses.Member

/**
 * Created by Hector Chirinos on 20/08/2023.
 */

interface OnClick {
 fun share(member: Member)
 fun call(member: Member)
}