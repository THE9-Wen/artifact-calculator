#![allow(dead_code)]
#![allow(unused_variables)]
use std::fs;
use std::fs::{File, read_dir};
use std::io::Write;
use std::path::{Path, PathBuf};

use image::ImageError;
use tesseract::Tesseract;
use crate::artifact::{Artifact, ParseError};

pub struct PreAnalyzer {
    suit_path: String,
    single_path: String,
}

impl PreAnalyzer {
    pub fn new(suit_path: String, single_path: String) -> Self {
        Self {
            suit_path,
            single_path,
        }
    }

    pub fn pre_analyze(&self) -> Result<(), ImageError> {
        self.crop_images(&self.suit_path)?;
        self.crop_images(&self.single_path)?;
        Self::analyze_artifact_pictures("artifacts/single/cropped")?;
        Self::analyze_artifact_pictures("artifacts/suit/cropped")?;
        Ok(())
    }

    fn crop_images(&self, dir_path: &str) -> Result<(), ImageError> {
        if !Path::new(format!("{}/{}", dir_path, "cropped").as_str()).exists() {
            fs::create_dir(format!("{}/{}", dir_path, "cropped"))?;
        }
        let mut index = 0;
        for file in read_dir(dir_path)? {
            let file = file?;
            let path = file.path();
            if self.is_image(&path) {
                println!("{}", path.to_str().unwrap());
                let mut picture = image::open(path)?;
                let width = picture.width();
                let height = picture.height();
                let mut picture = picture.crop(width / 3 * 2, 0, width, height / 3 * 2);
                picture.invert();
                let mut picture = picture.to_luma8();
                let picture = imageproc::contrast::stretch_contrast(&mut picture, 100, 150);
                picture.save(format!("{}/cropped/{}.png", dir_path, index))?;
                index += 1;
            }
        }
        Ok(())
    }

    pub fn is_image(&self, path: &PathBuf) -> bool {
        match path.extension() {
            Some(extension) => {
                extension.eq("PNG")
                    || extension.eq("png")
                    || extension.eq("jpeg")
                    || extension.eq("JPEG")
            },
            None => false
        }
    }

    pub fn get_artifact_info(path: &str) -> Option<String> {
        let mut tess = Tesseract::new(Some("resource"), Some("chi_sim")).unwrap()
            .set_variable("tessedit_char_whitelist",
                          "1234567890%+.生命值攻击力暴伤害率元素充能效防御水火冰雷岩草风精通沉沦之心饰金辰砂梦角斗士花死羽空杯时沙理冠提高加成").unwrap()
            .set_image(path).unwrap();
        let result = tess.get_text();
        if let Ok(info) = result {
            Some(info.replace(" ", "").replace("\n", ""))
        } else {
            None
        }
    }

    fn analyze_artifact_pictures(path: &str) -> Result<Vec<Artifact>, std::io::Error> {
        let mut suits = vec![];
        let path_str = format!("{}/../artifacts.json", path);
        let json_path = Path::new(&path_str);
        let mut json_file = File::create(json_path).unwrap();
        json_file.write_all(b"[")?;
        for file in fs::read_dir(path).unwrap() {
            let file = file.unwrap();
            let file_path = file.path();
            let file_path = file_path.to_str().unwrap();
            let artifact_str =
                PreAnalyzer::get_artifact_info(file_path).unwrap();
            let artifact = Artifact::from(&artifact_str, String::from(file.file_name().to_str().unwrap()));
            json_file.write_all(serde_json::to_string(&artifact).unwrap().as_bytes())?;
            json_file.write_all(b",")?;
            for error in &artifact.error {
                match error {
                    ParseError::SuitError => println!("Fail to analyze suit of artifact: {}", file_path),
                    ParseError::PositionErr => println!("Fail to analyze position of artifact: {}", file_path),
                    ParseError::SubError => println!("Fail to analyze sub keywords of artifact: {}", file_path),
                };
            }
            suits.push(artifact);
        }
        json_file.write_all(b"]")?;
        Ok(suits)
    }
}

#[cfg(test)]
mod test {
    use std::collections::HashSet;

    use unicode_segmentation::UnicodeSegmentation;

    use crate::PreAnalyzer;

    #[test]
    fn test_pre_analyze() {
        let analyzer = PreAnalyzer::new(String::from("artifacts/suit/"), String::from("artifacts/single"));
        analyzer.pre_analyze().unwrap();
    }

    #[test]
    fn test_get_artifact_info() {
        PreAnalyzer::get_artifact_info("artifacts/single/cropped/2.png");
    }

    #[test]
    fn reduce() {
        let mut result = String::new();
        let source = "1234567890%+.生命值攻击力暴击伤害率元素充能效防御水火冰雷岩草风精通沉沦之心饰金梦角斗士生之花死之羽空之杯时之沙理之冠";
        let mut set = HashSet::new();
        for (index, c) in source.graphemes(true).enumerate() {
            if !set.contains(c) {
                result.push_str(c);
                set.insert(c);
            }
        }
        println!("{}", result);
    }
}
