package com.example.baseproject.field

import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.baseproject.DatePickerFragment
import com.example.baseproject.R
import com.example.baseproject.databinding.ItemFieldBinding
import com.example.baseproject.databinding.ItemFieldChooserBinding
import com.example.baseproject.databinding.ItemFieldDateBinding

class FieldAdapter(
    private val fragmentManager: FragmentManager,
    private val fields: List<FieldDto>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val FIELD_TYPE_INPUT = 1
        const val FIELD_TYPE_DATE = 2
        const val FIELD_TYPE_SPINNER = 3
    }

    override fun getItemViewType(position: Int): Int {
        return when (fields[position].fieldTypeEnum) {
            FieldType.DATE -> FIELD_TYPE_DATE
            FieldType.GENDER -> FIELD_TYPE_SPINNER
            else -> FIELD_TYPE_INPUT
        }
    }

    class InputFieldViewHolder(private val binding: ItemFieldBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(field: FieldDto) {
            binding.apply {
                etField.hint = field.hint
                etField.inputType = field.fieldTypeEnum.inputType

                etField.addTextChangedListener {
                    field.value = it.toString()
                }
                Glide.with(itemView.context).load(field.icon).apply(
                    RequestOptions().placeholder(R.drawable.ic_image_issue)
                        .error(R.drawable.ic_image_issue)
                )
                    .into(ivIcon)
                Log.d("Jemali", "${field.icon}")

            }
        }
    }

    class GenderFieldViewHolder(private val binding: ItemFieldChooserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(field: FieldDto) {
            binding.apply {
                val genderOptions = arrayOf(
                    itemView.context.getString(R.string.select_gender),
                    itemView.context.getString(
                        R.string.male
                    ), itemView.context.getString(R.string.female)
                )
                val adapter = ArrayAdapter(
                    itemView.context,
                    android.R.layout.simple_spinner_dropdown_item,
                    genderOptions
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerField.adapter = adapter

                spinnerField.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        field.value = if (position > 0) genderOptions[position] else ""
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        field.value = ""
                    }
                }

                Glide.with(itemView.context).load(field.icon).apply(
                    RequestOptions().placeholder(R.drawable.ic_image_issue)
                        .error(R.drawable.ic_image_issue)
                )
                    .into(ivIcon)
            }
        }
    }

    class DateFieldViewHolder(private val binding: ItemFieldDateBinding) :
        RecyclerView.ViewHolder(binding.root), DatePickerFragment.DatePickerListener {
        fun onBind(field: FieldDto, fragmentManager: FragmentManager) {
            binding.apply {
                etField.isEnabled = field.isActive
                etField.hint = field.hint
                etField.setOnClickListener {
                    val datePickerFragment = DatePickerFragment()
                    datePickerFragment.setDatePickerListener(this@DateFieldViewHolder)
                    datePickerFragment.show(fragmentManager, "datePicker")
                }
                etField.addTextChangedListener {
                    field.value = it.toString()
                }

                Glide.with(itemView.context).load(field.icon).apply(
                    RequestOptions().placeholder(R.drawable.ic_image_issue)
                        .error(R.drawable.ic_image_issue)
                )
                    .into(ivIcon)
            }
        }

        override fun onDateSelected(year: Int, month: Int, day: Int) {
            val date = "$day/${month + 1}/$year"
            binding.etField.setText(date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FIELD_TYPE_INPUT -> InputFieldViewHolder(
                ItemFieldBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

            FIELD_TYPE_SPINNER -> GenderFieldViewHolder(
                ItemFieldChooserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            FIELD_TYPE_DATE -> DateFieldViewHolder(
                ItemFieldDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else -> InputFieldViewHolder(
                ItemFieldBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int = fields.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val field = fields[position]
        when (holder) {
            is InputFieldViewHolder -> {
                holder.onBind(field)
            }

            is GenderFieldViewHolder -> {
                holder.onBind(field)
            }

            is DateFieldViewHolder -> {
                holder.onBind(field, fragmentManager)
            }
        }
    }
}