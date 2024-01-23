package com.skysam.enlacehospitales.ui.common

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.skysam.enlacehospitales.R
import com.skysam.enlacehospitales.dataClasses.emergency.Emergency

/**
 * Created by Hector Chirinos on 22/01/2024.
 */

object CreatePDF {

 fun generatePDF(emergency: Emergency, context: Context) {
  val inflater = LayoutInflater.from(context)
  val view = inflater.inflate(R.layout.layout_pdf_1, null)

  val notif = view.findViewById<TextView>(R.id.tv_notification)
  notif.text = emergency.notification?.personCall

  val displayMetrics = DisplayMetrics()
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
   context.display?.getRealMetrics(displayMetrics)
   displayMetrics.densityDpi
  }
  else{
   (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
  }
  view.measure(
   View.MeasureSpec.makeMeasureSpec(
    displayMetrics.widthPixels, View.MeasureSpec.EXACTLY
   ),
   View.MeasureSpec.makeMeasureSpec(
    displayMetrics.heightPixels, View.MeasureSpec.EXACTLY
   )
  )
  view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)

  val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
  val canvas = Canvas(bitmap)
  view.draw(canvas)
  Bitmap.createScaledBitmap(bitmap, view.measuredWidth, view.measuredHeight, true)



  val inflater2 = LayoutInflater.from(context)
  val view2 = inflater2.inflate(R.layout.layout_pdf_2, null)

  val patient = view2.findViewById<TextView>(R.id.tv_patient)
  patient.text = emergency.patient?.name

  val displayMetrics2 = DisplayMetrics()
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
   context.display?.getRealMetrics(displayMetrics)
   displayMetrics2.densityDpi
  }
  else{
   (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
  }
  view2.measure(
   View.MeasureSpec.makeMeasureSpec(
    displayMetrics.widthPixels, View.MeasureSpec.EXACTLY
   ),
   View.MeasureSpec.makeMeasureSpec(
    displayMetrics.heightPixels, View.MeasureSpec.EXACTLY
   )
  )
  view2.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)

  val bitmap2 = Bitmap.createBitmap(view2.measuredWidth, view2.measuredHeight, Bitmap.Config.ARGB_8888)
  val canvas2 = Canvas(bitmap2)
  view2.draw(canvas2)
  Bitmap.createScaledBitmap(bitmap, view2.measuredWidth, view2.measuredHeight, true)




  val pdfDocument = PdfDocument()
  val pageInfo = PdfDocument.PageInfo.Builder(view.measuredWidth, view.measuredHeight, 1).create()
  val page = pdfDocument.startPage(pageInfo)
  page.canvas.drawBitmap(bitmap, 0F, 0F, null)
  pdfDocument.finishPage(page)

  val pageInfo2 = PdfDocument.PageInfo.Builder(view2.measuredWidth, view2.measuredHeight, 2).create()
  val page2 = pdfDocument.startPage(pageInfo2)
  page2.canvas.drawBitmap(bitmap2, 0F, 0F, null)
  pdfDocument.finishPage(page2)
 }

}