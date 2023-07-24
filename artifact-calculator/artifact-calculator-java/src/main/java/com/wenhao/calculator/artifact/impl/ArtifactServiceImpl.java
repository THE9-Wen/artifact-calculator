package com.wenhao.calculator.artifact.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wenhao.calculator.artifact.ArtifactService;
import com.wenhao.calculator.artifact.enums.Position;
import com.wenhao.calculator.artifact.enums.Suit;
import com.wenhao.calculator.artifact.mapper.ArtifactMapper;
import com.wenhao.calculator.artifact.model.Artifact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.reactive.JdkHttpClientResourceFactory;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ArtifactServiceImpl implements ArtifactService {
    private final ArtifactMapper artifactMapper;

    @Autowired
    public ArtifactServiceImpl(ArtifactMapper artifactMapper) {
        this.artifactMapper = artifactMapper;
    }

    @Override
    public List<Artifact> list() {
        return this.artifactMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public Artifact getById(Long id) {
        return this.artifactMapper.selectById(id);
    }

    @Override
    public Artifact getByPosition(Position position) {
        QueryWrapper<Artifact> wrapper = new QueryWrapper<Artifact>().eq("position", position.getKey());
        return this.artifactMapper.selectOne(wrapper);
    }

    @Override
    public Artifact getBySuit(Suit suit) {
        QueryWrapper<Artifact> wrapper = new QueryWrapper<Artifact>().eq("position", suit.getKey());
        return this.artifactMapper.selectOne(wrapper);
    }

    @Override
    public Artifact createArtifact(Artifact artifact) {
        int insert = this.artifactMapper.insert(artifact);
        return artifact;
    }

    @Override
    public Artifact updateArtifact(Artifact artifact) {
        int update = this.artifactMapper.updateById(artifact);
        return artifact;
    }

    @Override
    public void deleteArtifact(Long id) {
        int delete = this.artifactMapper.deleteById(id);
    }

    @Override
    public List<Suit> getSuits() {
        QueryWrapper<Artifact> suitQueryWrapper = new QueryWrapper<>();
        suitQueryWrapper.select("suit").groupBy("suit");
        List<Artifact> artifacts = this.artifactMapper.selectList(suitQueryWrapper);
        return artifacts.stream().map(Artifact::getSuit).distinct().collect(Collectors.toList());
    }

    @Override
    public List<Artifact> selectByIds(List<Long> ids) {
        QueryWrapper<Artifact> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        return this.artifactMapper.selectList(queryWrapper);
    }

    @Override
    public Boolean batchAdd(String uid) {
        return null;
    }
}
