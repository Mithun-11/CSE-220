import os
import numpy as np
import matplotlib.image as mpimg

def check_images_match(img_path1, img_path2):
    """Reads two images as pixel arrays and checks if they are visually identical."""
    if not os.path.exists(img_path1) or not os.path.exists(img_path2):
        return False
        
    try:
        img1 = mpimg.imread(img_path1)
        img2 = mpimg.imread(img_path2)
        
        # Check if dimensions are the same
        if img1.shape != img2.shape:
            return False
            
        # Check if all pixels match exactly
        return np.array_equal(img1, img2)
    except Exception as e:
        print(f"    Error reading images: {e}")
        return False

def check_text_match(file_path1, file_path2):
    """Reads two text files, normalizes slashes and line breaks, and compares them."""
    try:
        with open(file_path1, 'r', encoding='utf-8') as f1, open(file_path2, 'r', encoding='utf-8') as f2:
            text1 = f1.read().replace('\\', '/').replace('\r\n', '\n')
            text2 = f2.read().replace('\\', '/').replace('\r\n', '\n')
            return text1 == text2
    except Exception as e:
        print(f"    Error reading text files: {e}")
        return False

def compare_directories():
    expected_dir = "expected_outputs"
    actual_dir = "outputs"

    if not os.path.exists(expected_dir) or not os.path.exists(actual_dir):
        print("Error: Make sure both 'expected_outputs' and 'outputs' folders exist!")
        return

    # Find all test case folders (1, 2, 3, 4)
    test_cases = sorted([d for d in os.listdir(expected_dir) if os.path.isdir(os.path.join(expected_dir, d))])
    
    all_passed = True

    for test_case in test_cases:
        print(f"\n--- Checking Test Case {test_case} ---")
        exp_case_dir = os.path.join(expected_dir, test_case)
        act_case_dir = os.path.join(actual_dir, test_case)

        # 1. Check report.txt
        exp_report = os.path.join(exp_case_dir, "report.txt")
        act_report = os.path.join(act_case_dir, "report.txt")
        
        if os.path.exists(exp_report) and os.path.exists(act_report):
            # Using the new text comparison function here
            if check_text_match(exp_report, act_report):
                print("  ✅ report.txt matches perfectly.")
            else:
                print("  ❌ report.txt DOES NOT MATCH.")
                all_passed = False
        else:
            print("  ⚠️ report.txt missing in one or both directories.")
            all_passed = False

        # 2. Check images in 'plot' and 'color' folders
        for img_folder in ["plot", "color"]:
            exp_img_dir = os.path.join(exp_case_dir, img_folder)
            act_img_dir = os.path.join(act_case_dir, img_folder)
            
            if not os.path.exists(exp_img_dir):
                continue
                
            images = [f for f in os.listdir(exp_img_dir) if f.endswith(".png")]
            
            for img_name in images:
                exp_img_path = os.path.join(exp_img_dir, img_name)
                act_img_path = os.path.join(act_img_dir, img_name)
                
                if check_images_match(exp_img_path, act_img_path):
                    print(f"  ✅ {img_folder}/{img_name} matches perfectly.")
                else:
                    print(f"  ❌ {img_folder}/{img_name} DOES NOT MATCH (or is missing).")
                    all_passed = False

    print("\n===============================")
    if all_passed:
        print("🎉 ALL TESTS PASSED! Your code is perfect. 🎉")
    else:
        print("⚠️ SOME TESTS FAILED. Check the output above. ⚠️")
    print("===============================\n")

if __name__ == "__main__":
    compare_directories()