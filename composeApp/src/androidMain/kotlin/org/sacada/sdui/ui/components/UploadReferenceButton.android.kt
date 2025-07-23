package org.sacada.sdui.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun UploadReferenceButton(onFileUploaded: (ByteArray, String) -> Unit) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val name = uri.lastPathSegment ?: "file"
            context.contentResolver.openInputStream(uri)?.use { stream ->
                val bytes = stream.readBytes()
                onFileUploaded(bytes, name)
            }
        }
    }
    Button(onClick = { launcher.launch("*/*") }) {
        Text("Upload Reference")
    }
}
