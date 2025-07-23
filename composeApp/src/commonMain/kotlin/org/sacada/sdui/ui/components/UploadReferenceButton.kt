package org.sacada.sdui.ui.components

import androidx.compose.runtime.Composable

@Composable
expect fun UploadReferenceButton(onFileUploaded: (ByteArray, String) -> Unit)
