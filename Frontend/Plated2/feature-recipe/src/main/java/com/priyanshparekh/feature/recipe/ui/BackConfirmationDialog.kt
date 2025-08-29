package com.priyanshparekh.feature.recipe.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class BackConfirmationDialog(val onDiscard: () -> Unit): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle("Discard Recipe ?")
            .setMessage("If you go back now, unsaved changes will be lost.\nAre you sure you want to go back ?")
            .setPositiveButton("Continue Editing") { _, _, -> null }
            .setNegativeButton("Discard") { _, _ -> onDiscard() }
            .create()
    }
}