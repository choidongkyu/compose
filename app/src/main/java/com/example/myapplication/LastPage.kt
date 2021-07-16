package androidx.compose.material.icons.filled
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

public val Icons.Filled.LastPage: ImageVector
    get() {
        if (_lastPage != null) {
            return _lastPage!!
        }
        _lastPage = materialIcon(name = "Filled.LastPage") {
            materialPath {
                moveTo(5.59f, 7.41f)
                lineTo(10.18f, 12.0f)
                lineToRelative(-4.59f, 4.59f)
                lineTo(7.0f, 18.0f)
                lineToRelative(6.0f, -6.0f)
                lineToRelative(-6.0f, -6.0f)
                close()
                moveTo(16.0f, 6.0f)
                horizontalLineToRelative(2.0f)
                verticalLineToRelative(12.0f)
                horizontalLineToRelative(-2.0f)
                close()
            }
        }
        return _lastPage!!
    }

private var _lastPage: ImageVector? = null