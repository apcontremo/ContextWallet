package com.contextwallet.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.contextwallet.R
import com.contextwallet.domain.model.DocumentState
import com.contextwallet.domain.model.DocumentWithState
import com.contextwallet.ui.theme.ActiveGreen
import com.contextwallet.ui.theme.ExpiredRed
import com.contextwallet.ui.theme.InactiveOrange
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DocumentCard(
    document: DocumentWithState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header with name and state badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = document.document.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                StateBadge(state = document.state)
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // State description
            if (document.state == DocumentState.ACTIVE) {
                if (document.document.globalMode) {
                    Text(
                        text = stringResource(R.string.state_active_desc),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else if (!document.document.countryCode.isNullOrEmpty()) {
                    Text(
                        text = document.document.countryCode!!,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    // Point validity
                    val point = document.nearestPoint
                    if (point != null) {
                        Column {
                            Text(
                                text = point.label ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            val dist = document.distanceToNearestPoint
                            if (dist != null) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "%.2f km".format(dist),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )

                                    if (dist > 0.5) {
                                        Spacer(modifier = Modifier.width(8.dp))
                                        val context = LocalContext.current
                                        TextButton(
                                            onClick = {
                                                try {
                                                    val uri = Uri.parse("geo:${point.latitude},${point.longitude}?q=${point.latitude},${point.longitude}(${point.label})")
                                                    val intent = Intent(Intent.ACTION_VIEW, uri)
                                                    context.startActivity(intent)
                                                } catch (e: Exception) {
                                                    // Handle case where no maps app is installed
                                                }
                                            },
                                            contentPadding = PaddingValues(0.dp),
                                            modifier = Modifier.height(24.dp)
                                        ) {
                                            Text(stringResource(R.string.nav_directions), style = MaterialTheme.typography.labelSmall)
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Text(
                            text = stringResource(R.string.state_active_desc),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                val stateDesc = when (document.state) {
                    DocumentState.INACTIVE_NOT_STARTED -> {
                        val startDate = document.document.startDate
                        val dateStr = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault()).format(java.util.Date(startDate))
                        stringResource(R.string.state_inactive_not_started_date_desc, dateStr)
                    }
                    DocumentState.INACTIVE_OUT_OF_RANGE -> {
                        val dist = document.distanceToNearestPoint
                        if (dist != null) stringResource(R.string.state_inactive_out_of_range_km_desc, dist)
                        else stringResource(R.string.state_inactive_out_of_range_desc)
                    }
                    DocumentState.EXPIRED -> stringResource(R.string.state_expired_desc)
                    else -> ""
                }
                Text(
                    text = stateDesc,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Date range
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            Text(
                text = "${dateFormat.format(Date(document.document.startDate))} - ${dateFormat.format(Date(document.document.endDate))}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Usage count if available
            if (document.useCount > 0) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (document.useCount == 1)
                            stringResource(R.string.used_times, document.useCount)
                        else
                            stringResource(R.string.used_times_plural, document.useCount),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun StateBadge(state: DocumentState) {
    val (color, icon) = when (state) {
        DocumentState.ACTIVE -> ActiveGreen to Icons.Default.CheckCircle
        DocumentState.INACTIVE_NOT_STARTED -> InactiveOrange to Icons.Default.Schedule
        DocumentState.INACTIVE_OUT_OF_RANGE -> InactiveOrange to Icons.Default.LocationOff
        DocumentState.EXPIRED -> ExpiredRed to Icons.Default.Cancel
    }
    val stateLabel = when (state) {
        DocumentState.ACTIVE -> stringResource(R.string.state_active)
        DocumentState.INACTIVE_NOT_STARTED -> stringResource(R.string.state_inactive_not_started)
        DocumentState.INACTIVE_OUT_OF_RANGE -> stringResource(R.string.state_inactive_out_of_range)
        DocumentState.EXPIRED -> stringResource(R.string.state_expired)
    }

    Surface(
        color = color.copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = color
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stateLabel,
                style = MaterialTheme.typography.labelSmall,
                color = color
            )
        }
    }
}
