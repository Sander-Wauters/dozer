package com.example.dozer.common.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.dozer.R
import com.example.dozer.machine.data.MockMachineDatasource
import com.example.dozer.machine.data.MachineDto
import com.example.dozer.common.ui.theme.DozerTheme

@Composable
fun IndexCard(
    imageUrl: String?,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    imageSize: Dp = 80.dp,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier.clickable { onClick() }
    ) {
        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(imageSize)
            )
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IndexCardPreview() {
    val data: MachineDto.Index = MockMachineDatasource().loadMachines()[0]
    DozerTheme {
        IndexCard(data.imageUrl, "${data.serialNumber} ${data.name}", data.description!!)
    }
}
