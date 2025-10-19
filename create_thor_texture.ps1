# Script to create a simple Thor hammer texture
Add-Type -AssemblyName System.Drawing

# Create a 16x16 bitmap for Minecraft texture
$bitmap = New-Object System.Drawing.Bitmap(16, 16)
$graphics = [System.Drawing.Graphics]::FromImage($bitmap)

# Set background to transparent
$graphics.Clear([System.Drawing.Color]::Transparent)

# Define colors for the hammer
$metalColor = [System.Drawing.Color]::FromArgb(192, 192, 192)  # Silver
$darkMetalColor = [System.Drawing.Color]::FromArgb(128, 128, 128)  # Dark silver
$blueColor = [System.Drawing.Color]::FromArgb(64, 64, 255)  # Blue for lightning
$brownColor = [System.Drawing.Color]::FromArgb(139, 69, 19)  # Brown for handle

# Create brushes
$metalBrush = New-Object System.Drawing.SolidBrush($metalColor)
$darkMetalBrush = New-Object System.Drawing.SolidBrush($darkMetalColor)
$blueBrush = New-Object System.Drawing.SolidBrush($blueColor)
$brownBrush = New-Object System.Drawing.SolidBrush($brownColor)

# Draw hammer head (8x8 square in the middle)
# Top part of hammer head
$graphics.FillRectangle($metalBrush, 4, 2, 8, 4)
$graphics.FillRectangle($darkMetalBrush, 5, 3, 6, 2)

# Bottom part of hammer head
$graphics.FillRectangle($metalBrush, 4, 10, 8, 4)
$graphics.FillRectangle($darkMetalBrush, 5, 11, 6, 2)

# Middle part of hammer head
$graphics.FillRectangle($metalBrush, 6, 6, 4, 4)
$graphics.FillRectangle($darkMetalBrush, 7, 7, 2, 2)

# Add some blue lightning effects
$graphics.FillRectangle($blueBrush, 5, 4, 1, 1)
$graphics.FillRectangle($blueBrush, 10, 4, 1, 1)
$graphics.FillRectangle($blueBrush, 5, 11, 1, 1)
$graphics.FillRectangle($blueBrush, 10, 11, 1, 1)

# Draw handle (vertical line)
$graphics.FillRectangle($brownBrush, 7, 12, 2, 4)

# Add some Nordic rune-like patterns
$graphics.FillRectangle($darkMetalBrush, 6, 5, 1, 1)
$graphics.FillRectangle($darkMetalBrush, 9, 5, 1, 1)
$graphics.FillRectangle($darkMetalBrush, 6, 10, 1, 1)
$graphics.FillRectangle($darkMetalBrush, 9, 10, 1, 1)

# Save the texture
$outputPath = "src\main\resources\assets\cursor\textures\item\thor_hammer.png"
$bitmap.Save($outputPath, [System.Drawing.Imaging.ImageFormat]::Png)

Write-Host "Thor hammer texture created and saved to: $outputPath" -ForegroundColor Green

# Clean up
$graphics.Dispose()
$bitmap.Dispose()
$metalBrush.Dispose()
$darkMetalBrush.Dispose()
$blueBrush.Dispose()
$brownBrush.Dispose()
