import { useState } from 'react';

// Interface for the return type of useImageUpload hook
export interface ImageUploadHook {
  selectedFile: File | null;
  photoPreview: string | null;
  isUploading: boolean;
  handleFileChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  uploadImage: () => Promise<string | null>;
  resetImage: () => void;
}

// Main hook for image upload functionality
export const useImageUpload = (initialPhotoUrl: string = ''): ImageUploadHook => {
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [photoPreview, setPhotoPreview] = useState<string | null>(initialPhotoUrl || null);
  const [isUploading, setIsUploading] = useState(false);

  // Handle file selection
  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      setSelectedFile(file);
      const reader = new FileReader();
      reader.onloadend = () => setPhotoPreview(reader.result as string);
      reader.readAsDataURL(file);
    }
  };

  // Upload image to Cloudinary
  const uploadImage = async (): Promise<string | null> => {
    if (!selectedFile) return null;

    setIsUploading(true);
    const uploadFormData = new FormData();
    uploadFormData.append("file", selectedFile);

    try {
      const response = await fetch("http://localhost:8082/CoffeeDev/api/upload", {
        method: "POST",
        mode: "cors",
        body: uploadFormData,
      });

      if (!response.ok) throw new Error("Upload failed");

      const data = await response.json();
      return data.url;
    } catch (error) {
      console.error("Error uploading image:", error);
      alert("Failed to upload image. Please try again.");
      return null;
    } finally {
      setIsUploading(false);
    }
  };

  // Reset image state
  const resetImage = () => {
    setSelectedFile(null);
    setPhotoPreview(null);
  };

  return {
    selectedFile,
    photoPreview,
    isUploading,
    handleFileChange,
    uploadImage,
    resetImage
  };
};

// Standalone function for uploading an image file
export const uploadImageFile = async (file: File): Promise<string | null> => {
  if (!file) return null;
  
  const uploadFormData = new FormData();
  uploadFormData.append("file", file);

  try {
    const response = await fetch("http://localhost:8082/CoffeeDev/api/upload", {
      method: "POST",
      mode: "cors",
      body: uploadFormData,
    });

    if (!response.ok) throw new Error("Upload failed");

    const data = await response.json();
    return data.url;
  } catch (error) {
    console.error("Error uploading image:", error);
    throw new Error("Failed to upload image");
  }
};